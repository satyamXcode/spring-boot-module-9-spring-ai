
package com.example.module9;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

public class JobApplicationTools {

    @Tool(description = "Get the current status of a job application for a company")
    public String getApplicationStatus(
            @ToolParam(description = "Company name, for example TCS or Infosys")
            String companyName) {

        if (companyName == null || companyName.isBlank()) {
            return "Please provide a company name.";
        }

        return switch (companyName.trim().toLowerCase()) {
            case "tcs" ->
                    "TCS application status: Interview";
            case "infosys" ->
                    "Infosys application status: Applied";
            case "wipro" ->
                    "Wipro application status: Selected";
            default ->
                    "No application found for " + companyName + ".";
        };
    }
}
