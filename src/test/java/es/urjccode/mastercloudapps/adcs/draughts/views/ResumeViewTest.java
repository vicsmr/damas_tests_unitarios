package es.urjccode.mastercloudapps.adcs.draughts.views;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import es.urjccode.mastercloudapps.adcs.draughts.controllers.ResumeController;
import es.urjccode.mastercloudapps.adcs.draughts.utils.Console;

@RunWith(MockitoJUnitRunner.class)
public class ResumeViewTest {

    @Mock
    ResumeController resumeController;

    @Mock
    Console console;

    @InjectMocks
    ResumeView resumeView;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void givenResumeViewWhenNewGameRequiereCorrectThenNotError() {
        when(console.readChar("¿Queréis jugar otra? (s/n): ")).thenReturn('s');
        resumeView.interact(resumeController);
        verify(resumeController).resume(true);
    }

    @Test
    public void givenResumeViewWhenEndGameRequiereCorrectThenNotError() {
        when(console.readChar("¿Queréis jugar otra? (s/n): ")).thenReturn('n');
        resumeView.interact(resumeController);
        verify(resumeController).resume(false);
    }

}