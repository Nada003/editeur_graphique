package utils.custom_list;

import java.util.LinkedList;

public class WatchedList<T> {
    private final LinkedList<T> list = new LinkedList<>();
    private final LinkedList<ListListener> listListeners = new LinkedList<>();
    public synchronized void addElement(T element){
        if (!list.contains(element)) {
            list.add(element);
            notifyAllListeners();
        }
    }
    public synchronized void removeElement(T element){
        list.remove(element);
        notifyAllListeners();
    }
    public synchronized void removeElement(int index){
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

}
