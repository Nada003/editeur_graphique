package utils;

import java.awt.*;
import java.io.Serializable;

public class SerializableStroke extends BasicStroke implements Serializable {
    public SerializableStroke( float width,
                              int cap,
                               int join,
                               float miterlimit,
                               float[] dash,
                               float dash_phase) {
        super(width,cap,join,miterlimit,dash,dash_phase);
    }
}
