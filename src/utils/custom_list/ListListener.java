package utils.custom_list;

import java.io.Serializable;

public interface ListListener extends Serializable {
   void notifyListChanged();
}
