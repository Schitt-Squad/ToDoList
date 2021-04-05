package todolist;

/**
 * @author Christian Liechty
 */

public class Search {
    //Instance Variables
    private String searchItem;
    private String filterItems;

    //Constructor
    public Search(String searchItem, String filterItems) {
        this.searchItem = searchItem;
        this.filterItems = filterItems;
    }

    //Define getters and setters
    public String getSearchItem() {
        return searchItem;
    }

    public void setSearchItem(String searchItem) {
        this.searchItem = searchItem;
    }

    public String getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(String filterItems) {
        this.filterItems = filterItems;
    }

    //Allows User to search
    //Return type & parameters need to be changed during full implementation
    public boolean search(){
        return true;
    }

    //Allows User to filter
    //Return type & parameters need to be changed during full implementation
    public boolean filter(){
        return true;
    }
}
