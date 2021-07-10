public class Question{
    private String question, choice1, choice2, choice3;
    private int answer;
    
    public void setQuestion(String q){
        question = q;
    }
    
    public void setChoice1 (String c1){
        choice1 = c1;
    }
    
    public void setChoice2 (String c2){
        choice2 = c2;
    }
    
    public void setChoice3 (String c3){
        choice3 = c3;
    }
    
    public void setAnswer(int a){
        answer = a;
    }
    
    public String getQuestion(){
        return question;
    }
    
    public String getChoice1 (){
        return choice1;
    }
    
    public String getChoice2 (){
        return choice2;
    }
    
    public String getChoice3 (){
        return choice3;
    }
    
    public int getAnswer(){
        return answer;
    }
}