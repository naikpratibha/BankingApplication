package BankingApplication;
import java.util.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;   



public class BankingApplication {
    static HashMap<String,bankdetails> hmap;
    static HashMap<String,dateoflasttran> dmap;
    BankingApplication(){
        hmap = new HashMap<String,bankdetails>();
        dmap = new HashMap<String,dateoflasttran>();
        bankdetails newdetails = new bankdetails("9192932212","Pratibha","Pratibha@123",15000000.0,"123AB");
        hmap.put(newdetails.accountno,newdetails);
        newdetails = new bankdetails("6398536880", "Arti", "Arti@123", 2173656524.0, "123AB");
        hmap.put(newdetails.accountno,newdetails);
        newdetails = new bankdetails("5816981123", "Rakshi", "Rakshi@123", 12980981.0, "RAK12");
        hmap.put(newdetails.accountno, newdetails);
    }
    static BankingApplication application = new BankingApplication();
    public boolean checkaccount(String accountno, String pword){           
        if(hmap.containsKey(accountno)==false){
            return false;
        }
        if(hmap.get(accountno).psword.compareTo(pword)<0){
            return false;
        }
        return true;
    }
    public boolean forgetpword(String accountno, String ifsccode){          
        if(hmap.containsKey(accountno)==false){
            return false;
        }
        if(hmap.get(accountno).ifsccode.compareTo(ifsccode)<0){
           return false;
        }
        return true;
    }
    public boolean changepword(String accountno, String pword){
        if(hmap.containsKey(accountno)==false){
            return false;
        }
        bankdetails detail = hmap.get(accountno);
        detail.psword = pword;
        hmap.put(accountno,detail);
        return true;
    }
    public boolean deleteAccount(String accountno, String pword){
        if(hmap.containsKey(accountno)==false){
            return false;
        }
        if(hmap.get(accountno).psword != pword){
            return false;
        }
        hmap.remove(accountno);
        return true;
    }
    public boolean addAccount(bankdetails details){
        if(hmap.containsKey(details.accountno)==true){
            return false;
        }
        if(details.accountno.length()<10 || details.accountno.length()>10){
            return false;
        }
        for(int i=0; i<details.ifsccode.length(); i++){
            char ch = details.ifsccode.charAt(i);
            if((ch>='0' && ch<='9') || (ch>='A' && ch<='Z')){
                continue;
            }
            else{
                return false;
            }
        }
        String accountno = details.accountno;
        hmap.put(accountno,details);
        dateoflasttran ndate = new dateoflasttran();
        dmap.put(accountno,ndate);
        return true;
    }
    public boolean crAmount(String accountno,String pword, double cramount){
        if(hmap.containsKey(accountno)==false){
            return false;
        }
        if(hmap.get(accountno).psword != pword){
            return false;
        }
        bankdetails curdetails = hmap.get(accountno);
        curdetails.balance += cramount;
        hmap.put(accountno,curdetails);
        dateoflasttran ndate = new dateoflasttran();
        dmap.put(accountno,ndate);
        return true;
    }

    public boolean drAmount(String accountno, String pword, double dramount){
        if(hmap.containsKey(accountno)==false){
            return false;
        }
        if(hmap.get(accountno).balance<dramount || hmap.get(accountno).psword != pword){
            return false;
        }
        bankdetails curdetails = hmap.get(accountno);
        curdetails.balance -= dramount;
        hmap.put(accountno,curdetails);
        dateoflasttran ndate = new dateoflasttran();
        dmap.put(accountno,ndate);
        return true;
    }
    
    public boolean checkbalance(String accountno, String pword){
        if(hmap.containsKey(accountno)==false){
            return false;
        }
        if(hmap.get(accountno).psword != pword){
            return false;
        }
        return true;
    }
    static public class bankdetails{
        String accountno;
        String name;
        String psword;
        double balance;
        String ifsccode;
        bankdetails(String accountno,String name,String psword,double balance,String ifsccode){
              this.accountno = accountno;
              this.name = name;
              this.psword = psword;
              this.balance = balance;
              this.ifsccode = ifsccode;             
        }
    }
    public class dateoflasttran{
        DateTimeFormatter dtf;
        LocalDateTime now;
        dateoflasttran(){
            this.dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
            this.now = LocalDateTime.now();  
        }
    }
    public static int Choice(){
        Scanner scn = new Scanner(System.in);
        int choice = -1;
        System.out.println("1. Create an account.");
        System.out.println("2. Credit amount in your account balance.");
        System.out.println("3. Withdraw amount from your account balance.");
        System.out.println("4. Delete an account.");
        System.out.println("5. Check current balance.");
        System.out.println("6. Exit.");
        choice = scn.nextInt();
        return choice;
    }
    public static void Choicefirst(){
        Scanner scn = new Scanner(System.in);
        System.out.print("Your your Account Number(10 digit number(0-9)) : ");
        String accountno = scn.next();
        System.out.print("Enter you name : ");
        String name = scn.next();
        System.out.print("Enter your password(0-9, A-Z, a-z , @, #, %, &, *) : ");
        String pword = scn.next();
        System.out.print("Enter your IFSC code (5 characters numbers(0-9) and alphabets(A-Z)) : ");
        String ifsc = scn.next();
        System.out.print("Enter the amount credited : ");
        double amount = scn.nextDouble();
        bankdetails ndetails = new bankdetails(accountno,name,pword,amount,ifsc);
        if(application.addAccount(ndetails)==false){
            System.out.println("Your account is not created");
            return;
        }
        System.out.println("Your account is successfully created.");
    }
    public static String[] LogIn(){
        Scanner scn = new Scanner(System.in);
        System.out.print("Enter your account Number : ");
        String accountno = scn.next();
        System.out.print("Enter your password : ");
        String pword = scn.next();
        if(application.checkaccount(accountno,pword)==false){
            System.out.println("The credencials are not correct");
            System.out.print("Forget password(Y/N) : ");
            String pw = scn.next();
            if(pw=="N"){              
                return new String[2];
            }
            System.out.print("Enter your Account Number : ");
            String acnumber = scn.next();
            System.out.print("Enter IFSC code : ");
            String ifsccode = scn.next();
            if(application.forgetpword(acnumber,ifsccode)==false){
                System.out.println("Your account is not present");
                return new String[2];
            }
            System.out.print("Enter your new Password : ");
            String nwpword = scn.next();
            System.out.print("Enter the Password : ");
            String repword = scn.next();
            if(nwpword.compareTo(repword)<0){
                System.out.print("Your password does not match");
                return new String[2];
            }
            if(application.changepword(acnumber,nwpword)==false){
                System.out.print("Your passwaord does not match.");
                return new String[2];
            }
            System.out.println("Your account password has successfully have changed");
        }
        String[] idpwd = new String[2];
        idpwd[0] = accountno;
        idpwd[1] = application.hmap.get(accountno).psword;
        return idpwd;
    }
    public static void ChoiceSecond(){
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter your login details : ");
        String[] idpwd = LogIn();
        System.out.print("Enter the amount to be credited : ");
        double amount = scn.nextDouble();
        if(application.crAmount(idpwd[0], idpwd[1], amount)==false){
            System.out.print("Amount is not being credeted");
            return;
        }
        System.out.println("Amount is successfully added to the current balance");
        System.out.print("Your current balance is : " + application.hmap.get(idpwd[0]).balance);
        return;
    }
    public static void ChoiceThird(){
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter your login details : ");
        String[] idpwd = LogIn();
        System.out.print("Enter the amount to be withdrawn : ");
        double amount = scn.nextDouble();
        if(application.drAmount(idpwd[0], idpwd[1], amount)==false){
            System.out.print("Amount is not being withdrawn");
            return;
        }
        System.out.println("Amount is successfully being withdrawn");
        System.out.print("Your current balance is : "+application.hmap.get(idpwd[0]).balance);
        return;
    }
    public static void ChoiceForth(){
        System.out.println("Enter your login details : ");
        String[] idpwd = LogIn();
        if(application.deleteAccount(idpwd[0], idpwd[1])==false){
            System.out.print("Account is not deleted");
            return;
        }
        System.out.print("Account deleted successfully");
        return;
    }
    public static void ChoiceFifth(){
        System.out.println("Enter your login details : ");
        String[] idpwd = LogIn();
        if(application.checkbalance(idpwd[0],idpwd[1])==false){
            System.out.print("Your account is not present");
            return;
        }
        bankdetails details = application.hmap.get(idpwd[0]);
        System.out.print("Your current balance is : " + details.balance);
        return;
    }
    public static void QuerySelected(int choice){
        switch(choice){                  // application id : 0 application password : 1
            case 1 : Choicefirst();
            break;
            case 2 : ChoiceSecond();  
            break;
            case 3 : ChoiceThird();
            break;
            case 4 : ChoiceForth();
            break;
            case 5 : ChoiceFifth();
            break;
            default:
            break;
        }
    }
    public static void main(String[] args){
        System.out.println("WELCOME TO THE OFFICIAL PAGE OF PNB");
        System.out.println("What are your queries?");
        int choice = Choice();
        if(choice==6) return;
        QuerySelected(choice);
        System.out.println();
        System.out.println("Enter your choice again : ");
        choice = Choice();
        if(choice==6) return;
        QuerySelected(choice);
        System.out.println();
        System.out.print("Enter your choice again : ");
        choice = Choice();
        if(choice==6) return;
        QuerySelected(choice);
        System.out.println();
        System.out.print("Enter your choice again : ");
        choice = Choice();
        if(choice==6) return;
        QuerySelected(choice);
        System.out.println();
        System.out.print("Enter your choice again : ");
        choice = Choice();
        if(choice==6) return;
        QuerySelected(choice);
    }
}
