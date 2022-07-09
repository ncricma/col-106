
public class Calculator{

    // ***** evaluatePostFix method *****
    public int evaluatePostFix(String s) throws InvalidPostfixException{
            
            MyStack stk = new MyStack();        // new stack created

            for(int i=0; i<s.length(); i++){
                char c = s.charAt(i);

                if(c == ' '){
                    continue;
                }

                else if(Character.isDigit(c)){

                    int n = 0;
                    while(Character.isDigit(c)){
                        n = n*10 + (int)(c-'0');
                        i++;
                        if(i<s.length()){
                            c = s.charAt(i);
                        }
                        else{
                            return n;
                        }
                    }
                    i--;

                    stk.push(n);    
                }

                else{
                    // checks if the stack is empty 
                    if(stk.isEmpty()){
                        throw new InvalidPostfixException("s");                        
                    }
                    else{
                        try{
                            int val1 = (int)stk.pop();
                            // if stack contains only one number
                            if(stk.isEmpty()){
                                throw new InvalidPostfixException("s");
                            }
                            int val2 = (int)stk.pop();
                            switch(c)
                            {
                                case '+':
                                stk.push(val2 + val1);
                                break;
                                case '-':
                                stk.push(val2 - val1);
                                break;
                                case '*':
                                stk.push(val2 * val1);
                                break;
                            }
                        } catch (EmptyStackException e){
                            System.out.println("stack is empty ");
                        }
                    }
                }
                
            }
            // if stack has some extra numbers 
            if(stk.size() != 1){
                throw new InvalidPostfixException("s");
            }
            else{
                try{
                    return (int)stk.pop();
                } catch (EmptyStackException e){
                    return 0;
                }
            }
        }

    // ***** convertExpression method ******

    // to check the precedence of the given operator
    private int pre(char x){      
        if(x=='+' || x=='-')
        return 1;

        if(x=='*')
        return 2;

        return -1;         
    }

    // to check if the char is operator 
    private boolean isOper(char x){
        return (x=='+' || x=='-' || x=='*' || x=='(' || x==')');
    }

    public String convertExpression(String s) throws InvalidExprException{

        MyStack stack = new MyStack();      // created empty stack 
        
        String ans = "";        // this is for answer

        for(int i=0; i<s.length(); i++){

            char c = s.charAt(i);
            switch (c) {
                case ' ':
                    continue;
                case '(':
                    stack.push(c);
                    break;
                case ')':
                    try{
                        char x = (char)stack.pop();
                        while(x != '('){
                            ans += x + " ";
                            x = (char)stack.pop();
                        }
                    } catch (EmptyStackException e){
                        throw new InvalidExprException("InvalidExprException");
                    }
                    break;
                case '*':
                    stack.push(c);
                    break;
                case '+':
                    int prev = i-1;     // to check if the string start with + 
                    while (prev >= 0 && s.charAt(prev) == ' '){
                        prev--;
                    }
                    if(prev < 0 || isOper(s.charAt(prev))){
                        throw new InvalidExprException("InvalidExprException");
                    }
                    if(!stack.isEmpty()){
                        char x;
                        try {
                            x = (char)stack.top();      // top element of stack
                            while(!stack.isEmpty() && pre(x) >= pre(c)){        // checking the precedence of char c and top element 
                                ans += x + " ";
                                x = (char)stack.pop();
                                x = (char)stack.top();
                            }
                        }
                        catch (EmptyStackException e){
                            throw new InvalidExprException("InvalidExprException");
                        }
                    }
                    stack.push(c);
                    break;

                case '-':
                    prev = i-1;     // to check if the string start with - 
                    while (prev >= 0 && s.charAt(prev) == ' '){
                        prev--;
                    }
                    if(prev < 0 || isOper(s.charAt(prev))){
                        throw new InvalidExprException("InvalidExprException");
                    }

                    if(!stack.isEmpty()){
                        char x;
                        try {
                            x = (char)stack.top();
                            while(!stack.isEmpty() && pre(x) >= pre(c)){
                                ans += x + " ";
                                x = (char)stack.pop();
                                x = (char)stack.top();
                            }
                        }
                        catch (EmptyStackException e){
                            throw new InvalidExprException("InvalidExprException");
                        }
                    }
                    stack.push(c);
                    break;
                default:
                    if(Character.isDigit(c)){
                        while(Character.isDigit(c)){
                            ans += c;
                            i++;
                            if(i<s.length()){
                                c = s.charAt(i);
                            }
                            else{
                                break;
                            }
                        }
                        ans += " ";
                        i--;
                    }
                    else{
                        throw new InvalidExprException("InvalidExprException");
                    }
                    break;
            }

        }

        while(!stack.isEmpty()){
            try {
                char c = (char)stack.pop();
                ans += c + " ";     
                if(c == '('){
                    throw new InvalidExprException("InvalidExprException");
                }
            } catch (EmptyStackException e) {
                throw new InvalidExprException("InvalidExprException");
            }
        }
        
        return ans.substring(0, ans.length()-1);
    }
}

class InvalidPostfixException extends Exception{
    InvalidPostfixException(String s){
        super(s);
    }
}

class InvalidExprException extends Exception{
    InvalidExprException(String s){
        super(s);
    }
}