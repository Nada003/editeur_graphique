package utils.custom_list;

import java.util.LinkedList;

public class WatchedList<T> {
    private int oldSize = 0;
    private final LinkedList<T> list = new LinkedList<>();
    private final LinkedList<ListListener> listListeners = new LinkedList<>();
    public synchronized void addElement(T element){
        if (!list.contains(element)) {
            oldSize = list.size();
            list.add(element);
            notifyAllListeners();
        }
    }
    public synchronized void removeElement(T element){
        oldSize = list.size();
        list.remove(element);
        notifyAllListeners();
    }
    public synchronized void removeElement(int index){
        oldSize = list.size();
        list.remove(index);
        notifyAllListeners();
    }

    public LinkedList<T> getList() {
        return list;
    }

    private void notifyAllListeners(){
        for (ListListener listListener : listListeners) listListener.notifyListChanged();
    }
    public void addListener(ListListener element) {
        if (!listListeners.contains(element)) {
            listListeners.add(element);
        }
    }

    /**
     * return true if the list got new element false if element has been removed  **/
    public boolean isNewElementAdded(){
        return oldSize<list.size();
    }

}
