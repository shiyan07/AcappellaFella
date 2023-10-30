package app;

import interface_adapter.SingerChoose.SingerChooseController;
import interface_adapter.SingerChoose.SingerChoosePresenter;
import interface_adapter.SingerChoose.SingerChooseViewModel;
import interface_adapter.ViewManagerModel;
import use_case.SingerChoose.SingerChooseDataAccessInterface;
import use_case.SingerChoose.SingerChooseInputBoundary;
import use_case.SingerChoose.SingerChooseInteractor;
import use_case.SingerChoose.SingerChooseOutputBoundary;
import view.SingerChooseView;

import javax.swing.*;
import java.io.IOException;

public class SingerChooseUseCaseFactory {
    private SingerChooseUseCaseFactory() {}

    public static SingerChooseView create(
            ViewManagerModel viewManagerModel,
            SingerChooseViewModel singerChooseViewModel,
            SingerChooseDataAccessInterface DAO) {
        try {
            SingerChooseController singerChooseController = createSingerChooseUseCase(viewManagerModel, singerChooseViewModel, DAO);
            return new SingerChooseView(singerChooseViewModel, singerChooseController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file.");
        }

        return null;
    }
    private static SingerChooseController createSingerChooseUseCase(ViewManagerModel viewManagerModel,
                                                                  SingerChooseViewModel singerChooseViewModel,
                                                                  SingerChooseDataAccessInterface DAO) throws IOException {

        // Notice how we pass this method's parameters to the Presenter.
        SingerChooseOutputBoundary singerChooseOutputBoundary = new SingerChoosePresenter(viewManagerModel, singerChooseViewModel);

        SingerChooseInputBoundary singerChooseInteractor = new SingerChooseInteractor(
                DAO, singerChooseOutputBoundary);

        return new SingerChooseController(singerChooseInteractor);
    }
}