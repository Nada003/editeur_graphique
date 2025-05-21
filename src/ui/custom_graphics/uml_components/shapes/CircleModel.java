package ui.custom_graphics.uml_components.shapes;

import ui.custom_graphics.uml_components.UMLModel;

import java.awt.*;
public class CircleModel implements UMLModel {

        private String text;
        private Color color;

        // Constructeur principal
        public CircleModel(String text, Color color) {
            this.text = text;
            this.color = color;
        }

        // Constructeur avec couleur par défaut
        public CircleModel(String text) {
            this(text, Color.BLUE);
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }


