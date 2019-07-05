package student_player;

import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoMove;

public class MyTools {
	 public static double minNode(PentagoBoardState state,double alpha,double beta) {
	    	PentagoBoardState subState;
	    	
	    	for(PentagoMove m: state.getAllLegalMoves()) {
	    		
	         	subState=(PentagoBoardState)state.clone();
	     		subState.processMove(m);
	     		beta=Math.min(beta,evaluation(subState));
	     		//pruning
	     		if(beta<=alpha) {
	     		
	     			return alpha;
	     		}
	     		
	    	}
	    	
	    	
	    	return beta;
	    	
	  }
	 
	 
	 //check for hte state with higher probability to win
	 public static boolean winCase(PentagoBoardState.Piece start,PentagoBoardState.Piece end, int self,int oppo,PentagoBoardState.Piece oppoColor) {
		 if(self==4 && oppo==0) {
	        	return true;
         }else if(self==4 && oppo==1 &&(start==oppoColor || end==oppoColor)) {
        	 return true;
         }else if(self ==5 &&( oppo==0 ||(start==oppoColor || end==oppoColor))){
        	 return true;
         }else {
        	 return false;
         }
	 }
	 
	 public static int maxRow(PentagoBoardState state,PentagoBoardState.Piece selfColor,PentagoBoardState.Piece oppoColor) {
		 int diffColor=0;
	     int sameColor=0;
	     int max=Integer.MIN_VALUE;
		 for (int i = 0; i < 6; i++) {
	            for (int j = 0; j < 6; j++) {
	            	if(state.getPieceAt(i, j)==selfColor){
	            		sameColor++;
	            	}else if(state.getPieceAt(i, j)==oppoColor) {
	            		diffColor++;
	            	}
	       
	            }
	            if(diffColor==5) {
	            	return Integer.MIN_VALUE;
	            }
	            
	            if(winCase(state.getPieceAt(i, 0),state.getPieceAt(i, 5),sameColor,diffColor,oppoColor)) {
	            	return Integer.MAX_VALUE;
	            }
	           
	            
	            if(sameColor > max) {
	            	max=sameColor;
	            }
	            diffColor=0;
	            sameColor=0;
	        }
		 
		 return max;
	 }
	 
	 
	 public static int maxColumn(PentagoBoardState state,PentagoBoardState.Piece selfColor,PentagoBoardState.Piece oppoColor) {
		 int diffColor=0;
	     int sameColor=0;
	     int max=Integer.MIN_VALUE;
		 for (int i = 0; i < 6; i++) {
	            for (int j = 0; j < 6; j++) {
	            	if(state.getPieceAt(j, i)==selfColor){
	            		sameColor++;
	            	}else if(state.getPieceAt(j, i)==oppoColor) {
	            		diffColor++;
	            	}
	       
	            }
	            if(diffColor==5) {
	            	return Integer.MIN_VALUE;
	            }
	            
	            if(winCase(state.getPieceAt(0, i),state.getPieceAt(5,i),sameColor,diffColor,oppoColor)) {
	            	return Integer.MAX_VALUE;
	            }
	           
	            
	            if(sameColor > max) {
	            	max=sameColor;
	            }
	            diffColor=0;
	            sameColor=0;
	        }
		 
		 return max;
	 }
	 
	 public static int diagonal(PentagoBoardState state,PentagoBoardState.Piece selfColor,PentagoBoardState.Piece oppoColor) {
		 int diffColor=0;
	     int sameColor=0;
	     int max=0;
	     //count main diagonal up-left to down-right
	     for (int i = 0; i < 6; i++) {
	        	if(state.getPieceAt(i, i)==selfColor) {
	        		sameColor=sameColor+ 1;
         	}else if(state.getPieceAt(i, i)==oppoColor) {
         		diffColor++;
         	}
         	
	        }
	        
	        if(diffColor>=5) {
         	return Integer.MIN_VALUE;
         }
	        if(winCase(state.getPieceAt(0, 0),state.getPieceAt(5, 5),sameColor,diffColor,oppoColor)) {
         	return Integer.MAX_VALUE;
         }
	        
	      max=max+sameColor;
	      
	     diffColor=0;
	     sameColor=0;
	        //count main diagonal up-right to down-left
	     for (int i = 0; i < 6; i++) {
	        	if(state.getPieceAt(i, 5-i)==selfColor) {
	        		sameColor=sameColor+ 1;
         	}else if(state.getPieceAt(i, 5-i)==oppoColor) {
         		diffColor++;
         	}
         	
	        	
	        }
	        if(diffColor>=5) {
         	return Integer.MIN_VALUE;
         }
	        
	     if(winCase(state.getPieceAt(5, 0),state.getPieceAt(0, 5),sameColor,diffColor,oppoColor)) {
         	return Integer.MAX_VALUE;
         }
	        
	     max=max+sameColor;
		 return max;
	 }
	 
	
 
	 
	 
	 public static int evaluation(PentagoBoardState state) {
	    	
	    	
	    	int self=0;//total value of state for player
	    	int oppo=0;//total value of state for opponent
	    	int max=0;
	    	PentagoBoardState.Piece selfColor;
	    	PentagoBoardState.Piece oppoColor;
	    	
	    	if(state.getTurnPlayer()==0) {
	    		selfColor=PentagoBoardState.Piece.WHITE;
	    		oppoColor=PentagoBoardState.Piece.BLACK;
	    	}else{
	    		selfColor =PentagoBoardState.Piece.BLACK;
	    		oppoColor=PentagoBoardState.Piece.WHITE;
	    	}
	    	
	/////////////////////////////////evaluate for player///////////////////////////////////////////////////////
	    	max=maxRow(state,selfColor,oppoColor);
	        if(max==Integer.MIN_VALUE||max==Integer.MAX_VALUE) {
	        	return max;
	        }else {
	        	self=self+max;
	        }
	    	
	        max=maxColumn(state,selfColor,oppoColor);
	        if(max==Integer.MIN_VALUE||max==Integer.MAX_VALUE) {
	        	return max;
	        }else {
	        	self=self+max;
	        }
	        
	        max=diagonal(state,selfColor,oppoColor);
	        if(max==Integer.MIN_VALUE||max==Integer.MAX_VALUE) {
	        	return max;
	        }else {
	        	self=self+max;
	        }
	      
	        
           ////////////////////////////////////////Evaluate for opponent ////////////////////////////////////////////
	    	
	        
	    	
	        max=maxRow(state,oppoColor,selfColor);
	        if(max==Integer.MIN_VALUE) {
	        	return Integer.MAX_VALUE;
	        }if(max==Integer.MAX_VALUE) {
	        	return Integer.MIN_VALUE;
	        }else {
	        	oppo=oppo+max;
	        }
	    	
	        max=maxColumn(state,oppoColor,selfColor);
	        if(max==Integer.MIN_VALUE) {
	        	return Integer.MAX_VALUE;
	        }if(max==Integer.MAX_VALUE) {
	        	return Integer.MIN_VALUE;
	        }else {
	        	oppo=oppo+max;
	        }
	        
	        max=diagonal(state,oppoColor,selfColor);
	        if(max==Integer.MIN_VALUE) {
	        	return Integer.MAX_VALUE;
	        }if(max==Integer.MAX_VALUE) {
	        	return Integer.MIN_VALUE;
	        }else {
	        	oppo=oppo+max;
	        }
	        

			//the heuristic of state is total value of state for player - total value of state for opponent

	    	return self-oppo;
	    	
	    	
	    	
	    }
	    

	   

	    
}
