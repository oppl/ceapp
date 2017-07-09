package at.meroff.itproject.cucumber.stepdefs;

import at.meroff.itproject.CeappApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = CeappApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
