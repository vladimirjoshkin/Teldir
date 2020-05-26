package com.teldir.core.report;

import java.util.Date;

public class Report {
    public String title;
    public String[] columnNames;

    public String getHead() {
        return "<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\"\n" +
                "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <title>" + this.title + "</title>\n" +
                "</head>";
    }

    public String formReport() {
        return getHead() + getBody();
    }

    public String getReport() {
        return formReport();
    }

    public String getBody() {
        String body = "<h1 style='text-align: center;'>" + title + "</h1>" +
                "<p style='text-align: left;'>Formed at " + new Date().toString() + "</p>";
        body += formTable();
        return body;
    };

    public String formTable() {
        String table = "<table style='width: 100%;'>";
        table += formTableHeader();
        table += formTableContent();
        table += "</table>";
        return table;
    };

    public String formTableHeader() {
        return formTableHeader(columnNames);
    }

    public static String formTableHeader(String[] columnNames) {
        String header = "<tr>";
        for(String name : columnNames) {
            header += "<th>" + name + "</th>";
        }
        header += "</tr>";
        return header;
    }

    public String formTableContent() {
        return null;
    };

    public static String td(String cellContent) {
        return "<td>" + cellContent + "</td>";
    }

    public static String td(int cellContent) {
        return "<td>" + cellContent + "</td>";
    }

    public static String tr(String rowContent) {
        return "<tr>" + rowContent + "</tr>";
    }

    public static String tr(int rowContent) {
        return "<tr>" + rowContent + "</tr>";
    }
}