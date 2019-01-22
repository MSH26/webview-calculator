package stcorp.development.webviewcalculator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class WebviewCalculatorActivity extends Activity {
    /** Called when the activity is first created. */
	WebView wv1;
	Button btnShowCalculator;
	Button btnShowWebPage;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
        setContentView(R.layout.main);
    }
    
    @Override
    public void onResume() {
        super.onResume();

                
        wv1 = (WebView)findViewById(R.id.webView1);
        btnShowCalculator = (Button)findViewById(R.id.btnShowCalculator);
    	btnShowWebPage = (Button)findViewById(R.id.btnShowWebPage);

        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.addJavascriptInterface(new WebAppInterface(this), "Android");
        wv1.loadUrl("file:///android_asset/webvi.html");
    }

    @Override
    public void onPause() {
        super.onPause();
        
        wv1 = null;
        btnShowCalculator = null;
        btnShowWebPage = null;
    }

    @Override
    protected void onStop() {
        super.onStop();

        setVisible(false); 
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        setVisible(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    
    public class WebAppInterface 
	{
		 Context mContext;
		 Double firstOne = 0.0;
		 Double secondOne = 0.0;
		 Double result = 0.0;
		 String operator = "";
		 boolean flag = false;
		 @JavascriptInterface
		 WebAppInterface(Context ctx){
		    this.mContext=ctx;
		 } 

		 @JavascriptInterface
		 public String addNum(String exp, String num) 
		 {
			 if(exp.length() == 0){
				 firstOne = Double.parseDouble(num);
				 return num;
			 }
			 if(exp.contains("+") && !exp.substring(exp.length()-1, exp.length()).equals("+")){
				 String[] numbers = exp.split("+");
				 firstOne = Double.parseDouble(numbers[0] + num);
				 secondOne = Double.parseDouble(numbers[1] + num);
			 }else if(exp.contains("-") && !exp.substring(exp.length()-1, exp.length()).equals("-")){
				 String[] numbers = exp.split("-");
				 firstOne = Double.parseDouble(numbers[0] + num);
				 secondOne = Double.parseDouble(numbers[1] + num);
			 }else if(exp.contains("*") && !exp.substring(exp.length()-1, exp.length()).equals("*")){
				 String[] numbers = exp.split("*");
				 firstOne = Double.parseDouble(numbers[0] + num);
				 secondOne = Double.parseDouble(numbers[1] + num);
			 }else if(exp.contains("/") && !exp.substring(exp.length()-1, exp.length()).equals("/")){
				 String[] numbers = exp.split("/");
				 firstOne = Double.parseDouble(numbers[0] + num);
				 secondOne = Double.parseDouble(numbers[1] + num);
			 }
			 else if(exp.contains("+") && exp.substring(exp.length()-1, exp.length()).equals("+")){
				 secondOne = Double.parseDouble(num);
			 }else if(exp.contains("-") && exp.substring(exp.length()-1, exp.length()).equals("-")){
				 secondOne = Double.parseDouble(num);
			 }else if(exp.contains("*") && exp.substring(exp.length()-1, exp.length()).equals("*")){
				 secondOne = Double.parseDouble(num);
			 }else if(exp.contains("/") && exp.substring(exp.length()-1, exp.length()).equals("/")){
				 secondOne = Double.parseDouble(num);
			 }
			 return num;
			 
	      }
		 
		 public String addOperator(String opr) 
		 {
			 if(flag){
				 if(operator.contains("+")){
					 result = summation(firstOne, secondOne);
				 }
				 else if(operator.contains("-")){
					 result = substraction(firstOne, secondOne);
				 }
				 else if(operator.contains("*")){
					 result = multiplication(firstOne, secondOne);
				 }
				 else if(operator.contains("/")){
					 result = division(firstOne, secondOne);
				 }
				 return result.intValue() + opr;
			 }else{
				 flag = true;
				 operator = opr;
				 return opr;
			 }
	     }
		 
		 public String getResult(String str) 
		 {
			 String str1 = str.substring(str.length()-1, str.length());
			 if(!str1.equals("+") || !str1.equals("-") || !str1.equals("/") || !str1.equals("*")){
				 if(operator.contains("+")){
					 result = summation(firstOne, secondOne);
				 }
				 else if(operator.contains("-")){
					 result = substraction(firstOne, secondOne);
				 }
				 else if(operator.contains("*")){
					 result = multiplication(firstOne, secondOne);
				 }
				 else if(operator.contains("/")){
					 result = division(firstOne, secondOne);
				 }
				 return result + "";
			 }
			 else{
				 return str;
			 }
		 }
		 

		    private Double summation(Double x, Double y){
		    	return x + y;
		    }
		    
		    
		    private Double substraction(Double x, Double y){
		    	return x - y;
		    }
		    
		   
		    private Double multiplication(Double x, Double y){
		    	return x * y;
		    }
		    
		    
		    private Double division(Double x, Double y){
		    	try{
		    		return x / y;
		    	}
		    	catch(ArithmeticException e){
					return 0.0;
				}
				catch(Exception e){
					return 0.0;
				}
		    }
		 
	}
    
    public void onClickShowWebPageMethod(View v){
    	if(btnShowWebPage == (Button)v){
    		wv1.loadUrl("http://www.aiub.edu/");
    	}
    }


    public void onClickShowCalculatorMethod(View v){
    	if(btnShowCalculator == (Button)v){
            wv1.loadUrl("file:///android_asset/webvi.html");
    	}
    }
    
}