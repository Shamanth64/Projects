package com.easy_way_bank.helpers;

public class HTML {
    public static String htmlEmailTemplate(String token, int code) {
        String url = "http://127.0.0.1:8205/verify?token=" + token + "&code=" + code;
        String emailTemplate;
        emailTemplate = "<!DOCTYPE html>\n" +
        "<html lang='en'>\n" +
        "<head>\n" +
        "    <meta charset='UTF-8'>\n" +
        "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" +
        "    <link rel='stylesheet' href='../CSS/email.css'>\n" +
        "    <title>Document</title>\n" +
        "    <style>\n" +
        "        *{\n" +
        "    box-sizing: border-box;\n" +
        "    font-family: Comfortaa;\n" +
        "    }\n" +
        "\n" +
        "    /* Main Body */\n" +
        "    body {\n" +
        "        height: 100vh;\n" +
        "        background-color: rgb(212, 222, 230);\n" +
        "        display: flex;\n" +
        "        align-items: center;\n" +
        "        justify-content: center;\n" +
        "\n" +
        "    }\n" +
        "\n" +
        "    .wrapper {\n" +
        "        width: 550px;\n" +
        "        height: auto;\n" +
        "        padding: 15px;\n" +
        "        background-color: white;\n" +
        "        border-radius: 7px;\n" +
        "\n" +
        "    }\n" +
        "\n" +
        "    .email-message-header {\n" +
        "        text-align: center;\n" +
        "    }\n" +
        "\n" +
        "    .company-name {\n" +
        "        width: 100%;\n" +
        "        font-size: 35px;\n" +
        "        color: grey;\n" +
        "        text-align: center;\n" +
        "    }\n" +
        "\n" +
        "    .welcome-text {\n" +
        "        text-align: center;\n" +
        "    }\n" +
        "\n" +
        "    .verify-account-btn {\n" +
        "        padding: 15px;\n" +
        "        background-color: rgb(0, 109, 252);\n" +
        "        text-decoration: none;\n" +
        "        color: white;\n" +
        "        border-radius: 5px;\n" +
        "    }\n" +
        "\n" +
        "    .copy-right {\n" +
        "        padding: 15px;\n" +
        "        color: grey;\n" +
        "        font-size: 14px;\n" +
        "        margin: 20px 0px;\n" +
        "        display: flex;\n" +
        "        align-items: center;\n" +
        "        justify-content: center;\n" +
        "    }\n" +
        "    </style>\n" +
        "</head>\n" +
        "<body>\n" +
        "    <!-- Wrapper -->\n" +
        "    <div class='wrapper'>\n" +
        "        <!-- Email Message Header -->\n" +
        "        <h2 class='email-message-header'>\n" +
        "            Welcome and Thank You for Choosing\n" +
        "        </h2>\n" +
        "        <!-- End of Email Message Header -->\n" +
        "\n" +
        "        <!-- Company Name -->\n" +
        "        <div class='company-name'>Easy Way Bank</div>\n" +
        "        <!-- End of Company Name -->\n" +
        "\n" +
        "        <hr>\n" +
        "\n" +
        "        <!-- Welcome Text -->\n" +
        "        <p class='welcome-text'>\n" +
        "            Your Account has been successfully registered, please\n" +
        "            click  below to verify your account\n" +
        "        </p>\n" +
        "        <!-- End of Welcome Text -->\n" +
        "        <br>\n" +
        "        <br>\n" +
        "\n" +
        "    <!-- Verify Button -->\n" +
        "    <center><a href='" + url + "' class='verify-account-btn'>Verify Account</a></center>\n" +
        "    <!-- End of Verify Button-->\n" +
        "\n" +
        "    <!-- Copyright Wrapper -->\n" +
        "    <div class='copy-right'>\n" +
        "        &copy; Copyright 2021. All Rights Reserved.\n" +
        "    </div>\n" +
        "    <!-- End of Copyright Wrapper -->\n" +
        "    </div>\n" +
        "    <!-- End of Wrapper -->\n" +
        "</body>\n" +
        "</html>";
        return emailTemplate;
    }
}
