package com.nkm90.HearMeWhenYouCanNotSeeMe;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.nkm90.HearMeWhenYouCanNotSeeMe.basic.BasicActivity;
import com.google.mediapipe.formats.proto.LandmarkProto;
import com.google.mediapipe.formats.proto.LandmarkProto.NormalizedLandmark;
import com.google.mediapipe.formats.proto.LandmarkProto.NormalizedLandmarkList;
import com.google.mediapipe.framework.PacketGetter;

import java.util.List;

public class MediaPipeActivity extends BasicActivity {

    private static final String TAG = "MediaPipeActivity";
    private static final String OUTPUT_LANDMARKS_STREAM_NAME = "multi_hand_landmarks";
    private List<NormalizedLandmarkList> multiHandLandmarks;

    private TextView gesture;
    private TextView result;

    private long timestamp;
    String sentence ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gesture = findViewById(R.id.gesture);
        result = findViewById(R.id.resultString);
        timestamp = System.currentTimeMillis();
        result.setOnClickListener(v -> {
            String message = result.getText().toString();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("MESSAGE", message);
            setResult(1, resultIntent);
            finish();
        });

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        processor.addPacketCallback(
                OUTPUT_LANDMARKS_STREAM_NAME,
                (packet) -> {
                    Log.d(TAG, "Received multi-hand landmarks packet.");
                    multiHandLandmarks =
                            PacketGetter.getProtoVector(packet, NormalizedLandmarkList.parser());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String letter = handGestureCalculator(multiHandLandmarks);
                            gesture.setText(letter);
                            if (timestamp + 2000 < System.currentTimeMillis()
                                    && !letter.equals("No hand detected")
                                    && !letter.equals("no gesture")){
                                addToSentence(letter);
                                timestamp = System.currentTimeMillis();
                            }
                        }
                    });
                    Log.d(
                            TAG,
                            "[TS:"
                                    + packet.getTimestamp()
                                    + "] "
                                    + getMultiHandLandmarksDebugString(multiHandLandmarks));
                });
    }
    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent();
        backIntent.putExtra("MESSAGE", "Back");
        setResult(1, backIntent);
        finish();
    }

    private String getMultiHandLandmarksDebugString(List<NormalizedLandmarkList> multiHandLandmarks) {
        if (multiHandLandmarks.isEmpty()) {
            return "No hand landmarks";
        }
        String multiHandLandmarksStr = "Number of hands detected: " + multiHandLandmarks.size() + "\n";
        int handIndex = 0;
        for (NormalizedLandmarkList landmarks : multiHandLandmarks) {
            multiHandLandmarksStr +=
                    "\t#Hand landmarks for hand[" + handIndex + "]: " + landmarks.getLandmarkCount() + "\n";
            int landmarkIndex = 0;
            for (NormalizedLandmark landmark : landmarks.getLandmarkList()) {
                multiHandLandmarksStr +=
                        "\t\tLandmark ["
                                + landmarkIndex
                                + "]: ("
                                + landmark.getX()
                                + ", "
                                + landmark.getY()
                                + ", "
                                + landmark.getZ()
                                + ")\n";
                ++landmarkIndex;
            }
            ++handIndex;
        }
        return multiHandLandmarksStr;
    }
    private String handGestureCalculator(List<NormalizedLandmarkList> multiHandLandmarks) {
        if (multiHandLandmarks.isEmpty()) {
            return "No hand detected";
        }
        // Different conditions for each of the finger positions
        boolean isLeft = false;
        boolean isRight = false;
        boolean indexStraightUp = false;
        boolean indexStraightDown = false;
        boolean middleStraightUp = false;
        boolean middleStraightDown = false;
        boolean ringStraightUp = false;
        boolean ringStraightDown = false;
        boolean pinkyStraightUp = false;
        boolean pinkyStraightDown = false;
        boolean thumbIsOpen = false;
        boolean thumbIsBend = false;
        boolean palmIsVertical = false;
        boolean palmIsInclined = false;

        for (NormalizedLandmarkList landmarks : multiHandLandmarks) {
            List<NormalizedLandmark> landmarkList = landmarks.getLandmarkList();
            Log.d("Palm base", "" + landmarkList.get(0).getX() + " " + landmarkList.get(1).getX() + " " + landmarkList.get(2).getX() + " " + landmarkList.get(17).getX());
            Log.d("Palm base", "" + landmarkList.get(0).getY() + " " + landmarkList.get(1).getY() + " " + landmarkList.get(2).getY() + " " + landmarkList.get(17).getY());
            float pseudoFixKeyPoint = landmarkList.get(2).getX();

            if(pseudoFixKeyPoint > landmarkList.get(17).getX()) {
                isLeft = true;
            }else if (pseudoFixKeyPoint < landmarkList.get(17).getX()) {
                isRight = true;
            }
            Log.d(TAG, "pseudoFixKeyPoint == " + pseudoFixKeyPoint
                    + "\nlandmarkList.get(2).getX() == " + landmarkList.get(2).getX()
                    + "\nlandmarkList.get(17).getX() = " + landmarkList.get(17).getX());

            if (landmarkList.get(8).getY() < landmarkList.get(7).getY()
                    && landmarkList.get(7).getY() < landmarkList.get(6).getY()
                    && landmarkList.get(6).getY() < landmarkList.get(5).getY()){
                indexStraightUp = true;
            }else if (getEuclideanDistanceAB(landmarkList.get(8).getX(),landmarkList.get(8).getY(), landmarkList.get(0).getX(), landmarkList.get(0).getY())
                    < getEuclideanDistanceAB(landmarkList.get(5).getX(),landmarkList.get(5).getY(), landmarkList.get(0).getX(), landmarkList.get(0).getY())){
                indexStraightDown = true;
            }

            if (landmarkList.get(12).getY() < landmarkList.get(11).getY()
                    && landmarkList.get(11).getY() < landmarkList.get(10).getY()
                    && landmarkList.get(10).getY() < landmarkList.get(9).getY()){
                middleStraightUp = true;
            }else if (getEuclideanDistanceAB(landmarkList.get(12).getX(),landmarkList.get(12).getY(), landmarkList.get(0).getX(), landmarkList.get(0).getY()) <
                    getEuclideanDistanceAB(landmarkList.get(9).getX(),landmarkList.get(9).getY(), landmarkList.get(0).getX(), landmarkList.get(0).getY())){
                middleStraightDown = true;
            }

            if (landmarkList.get(16).getY() < landmarkList.get(15).getY()
                    && landmarkList.get(15).getY() < landmarkList.get(14).getY()
                    && landmarkList.get(14).getY() < landmarkList.get(13).getY()){
                ringStraightUp = true;
            } else if (getEuclideanDistanceAB(landmarkList.get(16).getX(),landmarkList.get(16).getY(), landmarkList.get(0).getX(), landmarkList.get(0).getY()) <
                    getEuclideanDistanceAB(landmarkList.get(13).getX(),landmarkList.get(13).getY(), landmarkList.get(0).getX(), landmarkList.get(0).getY())){
                ringStraightDown = true;
            }

            if (landmarkList.get(20).getY() < landmarkList.get(19).getY()
                    && landmarkList.get(19).getY() < landmarkList.get(18).getY()
                    && landmarkList.get(18).getY() < landmarkList.get(17).getY()){
                pinkyStraightUp = true;
            } else if (getEuclideanDistanceAB(landmarkList.get(20).getX(),landmarkList.get(20).getY(), landmarkList.get(0).getX(), landmarkList.get(0).getY()) <
                    getEuclideanDistanceAB(landmarkList.get(17).getX(),landmarkList.get(17).getY(), landmarkList.get(0).getX(), landmarkList.get(0).getY())){
                pinkyStraightDown = true;
            }

            if (getEuclideanDistanceAB(landmarkList.get(4).getX(),landmarkList.get(4).getY(), landmarkList.get(9).getX(), landmarkList.get(9).getY())
                    < getEuclideanDistanceAB(landmarkList.get(3).getX(),landmarkList.get(3).getY(), landmarkList.get(9).getX(), landmarkList.get(9).getY())){
                thumbIsBend = true;
            }else {
                thumbIsOpen = true;
            }

            if (landmarkList.get(0).getY() > landmarkList.get(2).getY()
                    && landmarkList.get(2).getY() > landmarkList.get(17).getY()){
                palmIsVertical = true;
            }else if (landmarkList.get(0).getY() > landmarkList.get(17).getY() &&
                    landmarkList.get(17).getY() >= landmarkList.get(2).getY())
                palmIsInclined = true;

            if (isRight){
                if (palmIsVertical){
                    if (indexStraightDown && middleStraightDown && ringStraightDown
                            && pinkyStraightDown && thumbIsOpen
                            && arePointsNear(landmarkList.get(4), landmarkList.get(6))
                            && landmarkList.get(4).getX() < landmarkList.get(6).getX())
                        return "A";
                    else if(thumbIsBend && indexStraightUp && middleStraightUp
                            && ringStraightUp && pinkyStraightUp)
                        return "B";
                    else if (thumbIsOpen && !arePointsNear(landmarkList.get(4), landmarkList.get(8))
                            && landmarkList.get(4).getX() >= landmarkList.get(8).getX()
                            && !arePointsNear(landmarkList.get(4), landmarkList.get(12))
                            && arePointsNear(landmarkList.get(8), landmarkList.get(12))
                            && arePointsNear(landmarkList.get(12), landmarkList.get(16))
                            && !arePointsNear(landmarkList.get(4), landmarkList.get(16))
                            && !arePointsNear(landmarkList.get(4), landmarkList.get(20))
                            && arePointsNear(landmarkList.get(16), landmarkList.get(20)))
                        return "C";
                    else if (indexStraightUp && thumbIsOpen &&
                            landmarkList.get(12).getX() <= landmarkList.get(4).getX() &&
                            arePointsNear(landmarkList.get(12), landmarkList.get(4)) &&
                            arePointsNear(landmarkList.get(12), landmarkList.get(16)) &&
                            arePointsNear(landmarkList.get(12), landmarkList.get(20)))
                        return "D";
                    else if (thumbIsBend && landmarkList.get(8).getY() < landmarkList.get(4).getY() &&
                            landmarkList.get(12).getY() < landmarkList.get(4).getY() &&
                            landmarkList.get(16).getY() < landmarkList.get(4).getY() &&
                            landmarkList.get(20).getY() < landmarkList.get(4).getY() &&
                            landmarkList.get(8).getY() >= landmarkList.get(5).getY() &&
                            landmarkList.get(12).getY() >= landmarkList.get(9).getY() &&
                            landmarkList.get(16).getY() >= landmarkList.get(13).getY() &&
                            landmarkList.get(20).getY() >= landmarkList.get(17).getY())
                        return "E";
                    else if (middleStraightUp && ringStraightUp && pinkyStraightUp
                            && thumbIsOpen && !indexStraightUp
                            && arePointsNear(landmarkList.get(8), landmarkList.get(4)))
                        return "F";
                    else if (arePointsNear(landmarkList.get(4), landmarkList.get(6))
                            && landmarkList.get(4).getX() < landmarkList.get(6).getX() &&
                            indexStraightDown && middleStraightDown && ringStraightDown
                            && pinkyStraightUp)
                        return "I";
                    else if(thumbIsOpen && landmarkList.get(4).getX() >= landmarkList.get(5).getX() &&
                            landmarkList.get(4).getX() <= landmarkList.get(9).getX() &&
                            indexStraightUp && middleStraightUp && ringStraightDown && pinkyStraightDown &&
                            getEuclideanDistanceAB(landmarkList.get(8).getX(), landmarkList.get(8).getY(), landmarkList.get(12).getX(), landmarkList.get(12).getY())
                                    > getEuclideanDistanceAB(landmarkList.get(5).getX(), landmarkList.get(5).getY(), landmarkList.get(9).getX(), landmarkList.get(9).getY()))
                        return "K";
                    else if(thumbIsOpen && landmarkList.get(4).getX() < landmarkList.get(3).getX() &&
                            landmarkList.get(4).getY() >= landmarkList.get(3).getY()
                            && indexStraightUp && middleStraightDown && ringStraightDown && pinkyStraightDown)
                        return "L";
                    else if(landmarkList.get(8).getY() > landmarkList.get(5).getY() &&
                            landmarkList.get(12).getY() > landmarkList.get(9).getY() &&
                            landmarkList.get(16).getY() > landmarkList.get(13).getY() &&
                            landmarkList.get(0).getY() < landmarkList.get(4).getY() &&
                            landmarkList.get(0).getY() < landmarkList.get(20).getY())
                        return "M";
                    else if(landmarkList.get(8).getY() > landmarkList.get(5).getY() &&
                            landmarkList.get(12).getY() > landmarkList.get(9).getY() &&
                            landmarkList.get(16).getY() < landmarkList.get(13).getY() &&
                            landmarkList.get(0).getY() < landmarkList.get(4).getY() &&
                            landmarkList.get(0).getY() < landmarkList.get(20).getY())
                        return "N";
                    else if(thumbIsOpen && arePointsNear(landmarkList.get(4), landmarkList.get(8))
                            && arePointsNear(landmarkList.get(8), landmarkList.get(12))
                            && arePointsNear(landmarkList.get(12), landmarkList.get(16))
                            && arePointsNear(landmarkList.get(16), landmarkList.get(20)))
                        return "O";
                    else if(thumbIsBend && indexStraightUp
                            && landmarkList.get(8).getX() >= landmarkList.get(12).getX()
                            && middleStraightUp && ringStraightDown
                            && landmarkList.get(4).getX() >= landmarkList.get(15).getX()
                            && pinkyStraightDown)
                        return "R";
                    else if(thumbIsBend && indexStraightDown && middleStraightDown && ringStraightDown && pinkyStraightDown &&
                            landmarkList.get(8).getY() >= landmarkList.get(5).getY() &&
                            landmarkList.get(7).getY() >= landmarkList.get(5).getY() &&
                            landmarkList.get(12).getY() >= landmarkList.get(9).getY() &&
                            landmarkList.get(11).getY() >= landmarkList.get(9).getY() &&
                            landmarkList.get(16).getY() >= landmarkList.get(13).getY() &&
                            landmarkList.get(15).getY() >= landmarkList.get(13).getY() &&
                            landmarkList.get(20).getY() >= landmarkList.get(17).getY() &&
                            landmarkList.get(19).getY() >= landmarkList.get(17).getY() &&
                            landmarkList.get(4).getX() > landmarkList.get(7).getX() &&
                            landmarkList.get(4).getY() <= landmarkList.get(11).getY())
                        return "S";
                    else if(thumbIsOpen && indexStraightDown && middleStraightDown && ringStraightDown
                            && pinkyStraightDown && landmarkList.get(4).getX() > landmarkList.get(6).getX()
                            && landmarkList.get(4).getX() < landmarkList.get(10).getX())
                        return "T";
                    else if(thumbIsBend && indexStraightUp && middleStraightUp
                            && ringStraightDown && pinkyStraightDown
                            && arePointsNear(landmarkList.get(8), landmarkList.get(12)))
                        return "U";
                    else if(thumbIsBend && indexStraightUp && middleStraightUp
                            && ringStraightDown && pinkyStraightDown
                            && !arePointsNear(landmarkList.get(8), landmarkList.get(12)))
                        return "V";
                    else if(thumbIsBend && indexStraightUp && middleStraightUp
                            && ringStraightUp && pinkyStraightDown
                            && !arePointsNear(landmarkList.get(8), landmarkList.get(12))
                            && !arePointsNear(landmarkList.get(16), landmarkList.get(12)))
                        return "W";
                    else if(thumbIsBend && landmarkList.get(8).getY() <= landmarkList.get(5).getY()
                            && landmarkList.get(8).getY() >= landmarkList.get(6).getY() &&
                            landmarkList.get(7).getY() >= landmarkList.get(5).getY() &&
                            landmarkList.get(12).getY() >= landmarkList.get(9).getY() &&
                            landmarkList.get(11).getY() >= landmarkList.get(9).getY() &&
                            landmarkList.get(16).getY() >= landmarkList.get(13).getY() &&
                            landmarkList.get(15).getY() >= landmarkList.get(13).getY() &&
                            landmarkList.get(20).getY() >= landmarkList.get(17).getY() &&
                            landmarkList.get(19).getY() >= landmarkList.get(17).getY() &&
                            landmarkList.get(4).getX() > landmarkList.get(11).getX())
                        return "X";
                    else if(thumbIsOpen && indexStraightDown && middleStraightDown
                            && ringStraightDown && pinkyStraightUp)
                        return "Y";
                    else if(thumbIsOpen && landmarkList.get(8).getY() < landmarkList.get(5).getY()
                            && landmarkList.get(8).getX() < landmarkList.get(5).getX()
                            && landmarkList.get(4).getY() >= landmarkList.get(3).getY()
                            && landmarkList.get(4).getX() >= landmarkList.get(9).getX()
                            && landmarkList.get(12).getY() > landmarkList.get(9).getY()
                            && landmarkList.get(16).getY() > landmarkList.get(13).getY()
                            && landmarkList.get(20).getY() > landmarkList.get(17).getY())
                        return "Z";
                }
                else if (palmIsInclined){
                    if(landmarkList.get(4).getY() < landmarkList.get(3).getY()
                            && landmarkList.get(3).getY() < landmarkList.get(2).getY()
                            && landmarkList.get(8).getY() < landmarkList.get(5).getY()
                            && landmarkList.get(12).getY() < landmarkList.get(9).getY()
                            && landmarkList.get(16).getY() < landmarkList.get(13).getY()
                            && landmarkList.get(20).getY() < landmarkList.get(17).getY()
                            && landmarkList.get(17).getY() >= landmarkList.get(2).getY())
                        return "SPACE";
                    else if (thumbIsOpen && indexStraightUp && middleStraightDown
                            && ringStraightDown && pinkyStraightDown
                            && landmarkList.get(8).getX() >= landmarkList.get(13).getX())
                        return "G";
                    else if (thumbIsBend && ringStraightDown && pinkyStraightDown
                            && indexStraightUp && middleStraightUp)
                        return "H";
                    else if(thumbIsBend && indexStraightDown && middleStraightDown
                            && ringStraightDown && pinkyStraightUp)
                        return "J";
                    else if(landmarkList.get(4).getY() > landmarkList.get(3).getY()
                            && landmarkList.get(3).getY() > landmarkList.get(2).getY()
                            && landmarkList.get(8).getX() < landmarkList.get(7).getX()
                            && landmarkList.get(7).getX() < landmarkList.get(6).getX()
                            && landmarkList.get(6).getX() < landmarkList.get(5).getX()
                            && landmarkList.get(12).getY() > landmarkList.get(11).getY()
                            && landmarkList.get(11).getY() > landmarkList.get(9).getY()
                            && landmarkList.get(16).getY() > landmarkList.get(15).getY()
                            && landmarkList.get(15).getY() > landmarkList.get(13).getY()
                            && landmarkList.get(20).getY() > landmarkList.get(19).getY()
                            && landmarkList.get(19).getY() > landmarkList.get(17).getY()
                            && arePointsNear(landmarkList.get(4), landmarkList.get(12)))
                        return "P";
                    else if(landmarkList.get(4).getY() > landmarkList.get(3).getY()
                            && landmarkList.get(3).getY() > landmarkList.get(2).getY()
                            && landmarkList.get(8).getY() > landmarkList.get(7).getY())
                        return "Q";
                }
            }
            else {
                String info = "thumbIsOpen " + thumbIsOpen + ", thumbIsBend " + thumbIsBend
                        + ", indexStraightUp " + indexStraightUp + ", indexStraightDown " + indexStraightDown
                        + ", middleStraightUp " + middleStraightUp + ", middleStraightDown " + middleStraightDown
                        + ", ringStraightUp " + ringStraightUp + ", ringStraightDown " + ringStraightDown
                        + ", pinkyStraightUp " + pinkyStraightUp + ", pinkyStraightDown " + pinkyStraightDown;
                Log.d(TAG, "handGestureCalculator: == " + info);
            }
        }
        return "no gesture";
    }

    private void addToSentence(String letter){
        if (letter.equals("SPACE"))
            letter = " ";
        sentence = result.getText().toString();
        sentence += letter;
        result.setText(sentence);
    }

    private boolean arePointsNear(LandmarkProto.NormalizedLandmark point1,
                                  LandmarkProto.NormalizedLandmark point2) {
        double distance = getEuclideanDistanceAB(point1.getX(),
                point1.getY(), point2.getX(), point2.getY());
        return distance < 0.1;
    }

    private double getEuclideanDistanceAB(double a_x, double a_y,
                                          double b_x, double b_y) {
        double dist = Math.pow(a_x - b_x, 2) + Math.pow(a_y - b_y, 2);
        return Math.sqrt(dist);
    }

    private double getAngleABC(double a_x, double a_y, double b_x, double b_y, double c_x, double c_y) {
        //Vector 1 (AB)
        double ab_x = b_x - a_x;
        double ab_y = b_y - a_y;
        //Vector 2 (CB)
        double cb_x = b_x - c_x;
        double cb_y = b_y - c_y;

        double dot = (ab_x * cb_x + ab_y * cb_y);   // dot product
        double cross = (ab_x * cb_y - ab_y * cb_x); // cross product

        return Math.atan2(cross, dot);
    }

    private int radianToDegree(double radian) {
        return (int) Math.floor(radian * 180. / Math.PI + 0.5);
    }
}
