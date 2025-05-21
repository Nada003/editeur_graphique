package ui.custom_graphics.uml_components.use_case_diagrame;

public class ActorModel {
    private String name;

    public ActorModel(String name, String[] par, String[] par1) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
