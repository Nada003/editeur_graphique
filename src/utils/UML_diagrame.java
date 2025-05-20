package utils;

public enum UML_diagrame {
    diagrameClass("diagramme de class"),
    diagrameSequence("diagramme de sequence"),
    diagrameCasUtilisation("diagramme de cas-utilisation");

    public String value;
    UML_diagrame(String value){
        this.value = value;
    }
}
