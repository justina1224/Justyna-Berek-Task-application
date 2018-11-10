package com.crud.kodillatasks.service;

import com.crud.kodillatasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private AdminConfig adminConfig;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://www.localhost:8888/tasks_frontend");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("preview_message", "Tasks: New Trello card");
        context.setVariable("goodbye_message", "Have a nice day");
        context.setVariable("company_details", adminConfig.getCompanyName() + "\n" +
                adminConfig.getCompanyEmail() + "\n" + adminConfig.getCompanyPhone());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildTrelloNewTaskEmail(String message) {

        List<String> options = new ArrayList<>();
        options.add("You can manage your tasks on Trello");
        options.add("You can either manage your tasks directly from app");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://trello.com/justyna874/boards");
        context.setVariable("button", "Visit Trello website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("preview_message", "Tasks: daily update");
        context.setVariable("goodbye_message", "Thank you for using Trello and see you soon :)");
        context.setVariable("company_details", adminConfig.getCompanyName() + "\n" +
                adminConfig.getCompanyEmail() + "\n" + " Phone: +48 " + adminConfig.getCompanyPhone());
        context.setVariable("show_button", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_options", options);
        return templateEngine.process("mail/new-tasks-info-mail", context);
    }
}
