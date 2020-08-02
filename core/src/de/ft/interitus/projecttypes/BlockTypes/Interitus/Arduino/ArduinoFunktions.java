package de.ft.interitus.projecttypes.BlockTypes.Interitus.Arduino;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextField;
import de.ft.interitus.UI.ManualConfig.DeviceConfiguration;
import de.ft.interitus.UI.UI;
import de.ft.interitus.UI.UIElements.dropdownmenue.DropDownElement;
import de.ft.interitus.UI.UIVar;
import de.ft.interitus.compiler.Arduino.ArduinoCompiler;
import de.ft.interitus.loading.AssetLoader;
import de.ft.interitus.projecttypes.ProjectManager;
import de.ft.interitus.projecttypes.ProjectFunktions;
import org.json.JSONArray;


public class ArduinoFunktions implements ProjectFunktions {
  private int counter = 0; //Counts the connected Boards
    private String savedidentifier = ""; //To know which is the Selected Board


    private VisTextField configurationname = new VisTextField();
    private DeviceConfiguration activeConfiguration;

    public ArduinoFunktions() {
        configurationname.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(activeConfiguration!=null) {
                    activeConfiguration.setName(((VisTextField) actor).getText());
                    activeConfiguration.updateEntry();
                }
            }
        });
    }


    @Override
    public void create() {

        ProjectManager.getActProjectVar().blocks.add(ProjectManager.getActProjectVar().projectType.getBlockGenerator().generateBlock(0, UIVar.abstandvonRand + 20, UIVar.programmflaeche_h + UIVar.untenhohe - 70 - UIVar.buttonbarzeile_h - 20, ProjectManager.getActProjectVar().projectType.getProjectblocks().get(0).getWidth(), UIVar.BlockHeight, ProjectManager.getActProjectVar().projectType.getProjectblocks().get(0), ProjectManager.getActProjectVar().projectType.getBlockUpdateGenerator(), ProjectManager.getActProjectVar().projectType.getBlocktoSaveGenerator()));
        ProjectManager.getActProjectVar().blocks.add(ProjectManager.getActProjectVar().projectType.getBlockGenerator().generateBlock(0, UIVar.abstandvonRand + 20, UIVar.programmflaeche_h + UIVar.untenhohe - 70 - UIVar.buttonbarzeile_h - 250, ProjectManager.getActProjectVar().projectType.getProjectblocks().get(1).getWidth(), UIVar.BlockHeight, ProjectManager.getActProjectVar().projectType.getProjectblocks().get(1), ProjectManager.getActProjectVar().projectType.getBlockUpdateGenerator(), ProjectManager.getActProjectVar().projectType.getBlocktoSaveGenerator()));

    }

    @Override
    public void update() {


        JSONArray array = ArduinoCompiler.getBoards();
        if (array == null) {
            return;//TODO there was an error
        }
        counter = 0;
        for (int i = 0; i < array.length(); i++) {
            if (array.getJSONObject(i).has("boards")) {
                counter++;


            }
        }


        if (counter != UI.runselection.getElements().size()) {
            if (UI.runselection.getSelectedElement() != null) {
                savedidentifier = ((String) UI.runselection.getSelectedElement().getIdentifier());
            }

            UI.runselection.clear();

            for (int i = 0; i < array.length(); i++) {
                if (array.getJSONObject(i).has("boards")) {

                    Texture image;


                    switch (array.getJSONObject(i).getJSONArray("boards").getJSONObject(0).getString("FQBN")) {
                        case "arduino:avr:uno":
                            image = AssetLoader.arduinounoimage;
                            break;
                        case "arduino:avr:mega":
                            image = AssetLoader.arduinomegaimage;
                            break;
                        case "arduino:avr:nano":
                            image = AssetLoader.arduinonanoimage;
                            break;
                        default:
                            image = AssetLoader.connector_offerd; //TODO image questionmark
                    }

                    UI.runselection.addelement(new DropDownElement(image, array.getJSONObject(i).getJSONArray("boards").getJSONObject(0).getString("name"), array.getJSONObject(i).getString("address")));
                    if (array.getJSONObject(i).getString("address").contains(savedidentifier)) {
                        UI.runselection.setSelectedElement(UI.runselection.getElements().get(UI.runselection.getElements().size() - 1));
                    }
                }
            }
            if (UI.runselection.getSelectedElement() == null && UI.runselection.getElements().size() > 0) {
                UI.runselection.setSelectedElement(UI.runselection.getElements().get(0));
            }

            if (UI.runselection.getSelectedElement() == null) {
                UI.runselection.setDefaultText("Bitte Gerät verbinden");
            }

        }


    }



    @Override
    public void runconfigsettings(VisTable builder, DeviceConfiguration configuration) {

        activeConfiguration = configuration;
        configurationname.setText(configuration.getName());
        builder.add(configurationname).expandX().fillY();



        configuration.updateEntry();

    }

    @Override
    public void changedrunconfig() {
        activeConfiguration = null;
    }

}
