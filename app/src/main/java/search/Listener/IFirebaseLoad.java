package search.Listener;

import java.util.List;

import search.Model.Person;

public interface IFirebaseLoad {
    void onFirebaseLoadSuccess(List<Person>personList);
    void onFirebaseLoadFailed(String message);
}
