package todolist

import spock.lang.Specification

class AppTests extends Specification {
    private ToDoListSys sys= ToDoListSys.instance("../User.json")

    def "Read In Users"(){
        setup:
        FileManager iO= new FileManager();
        ArrayList<User> test= iO.readUser("../User.json")
        expect:
        test.get(0).getFirstName()=="Foo"
    }

    def "Login Test"(){
        setup:
        FileManager iO= new FileManager();
        expect:
        sys.login(sys.getUserList().get(0).getUserName(), sys.getUserList().get(0).getPassword())


    }

    def "New List Test"(){
        setup:
        sys.getUserList().get(0).newList("projects", "stuff")
        expect:
        sys.getUserList().get(0).getList(0).getTitle()=="projects"

    }

    def "Save List Test"(){
        setup:
        sys.getUserList().get(0).newList("projects", "stuff")
        FileManager fileMang= new FileManager();
        fileMang.writeUser("../test.json", sys.getUserList())
        ArrayList<User> test= fileMang.readUser("../test.json")
        expect:
        test.get(0).getList(0).getTitle()=="projects"

    }


}
