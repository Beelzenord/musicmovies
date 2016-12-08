public class Controller {
    private Model model;
    private View view;
    private LogIn logIn;
    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
        view.ControllerHook(this);
      //  logIn.hookController(this);
    }
    public void handleButton(){
        System.out.println("Confirm");
    }
    public void transfer(String userName, String passWord) throws SQLException{
        System.out.println("fasdfa<");
        model.validateUserIdentity(userName,passWord);
    }
    public void confirm(){
        System.out.println("confirm");
    }
    public void connectToLogin(LogIn logIn){
        this.logIn = logIn;
    }
}
