package menu;
import javax.swing.*;
import otherforms.alertform;
import otherforms.buildingform;
import otherforms.energy_consumptionform;
import otherforms.powerusageform;
import otherforms.user;




import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;

	public class menuform extends JFrame {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;


		public menuform() {
	        setTitle("Menu");
	        setSize(400, 300);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        // Create buttons for each module
	        JButton registrationformButton = new JButton("Registrationform");
	        JButton powerusageButton = new JButton("powerusageform");
	        JButton userButton = new JButton("user");
	        JButton buildingsButton = new JButton("buildingform");
	        JButton alertButton = new JButton("arletform");
            JButton energy_consumptionButton = new JButton("energy_consumptionform");
	        // Add action listeners for each button
            
	        registrationformButton.addActionListener(new ActionListener() {
	           
	            public void actionPerformed(ActionEvent e) {
	               
	                dispose();
	                new registrationform().setVisible(true);
	            }
	        });
	        
	        buildingsButton.addActionListener(new ActionListener() {
		           
	            public void actionPerformed(ActionEvent e) {
	               
	                dispose();
	                new buildingform().setVisible(true);
	            }
	        });
	        
	        userButton.addActionListener(new ActionListener() {
		           
	            public void actionPerformed(ActionEvent e) {
	               
	                dispose();
	                new user().setVisible(true);
	            }
	        });

	        powerusageButton.addActionListener(new ActionListener() {
	  
	            public void actionPerformed(ActionEvent e) {
	                
	                dispose(); 
	                new powerusageform().setVisible(true); 
	            }
	        });

	        energy_consumptionButton.addActionListener(new ActionListener() {
	      	  
	            public void actionPerformed(ActionEvent e) {
	                
	                dispose(); 
	                new energy_consumptionform().setVisible(true); 
	            }
	        });
	        alertButton.addActionListener(new ActionListener() {
	      	  
	            public void actionPerformed(ActionEvent e) {
	                
	                dispose(); 
	                new alertform().setVisible(true); 
	            }
	        });
	       

	        // Create layout
	        JPanel panel = new JPanel();
	        panel.add(registrationformButton);
	        panel.add(alertButton);
	        panel.add(energy_consumptionButton);
	        panel.add(buildingsButton);
	        panel.add(powerusageButton);
	        panel.add(userButton);

	        getContentPane().add(panel);

	        setLocationRelativeTo(null); // Center the form
	    }

	   /* private void RegistrationformModule() {
	        // Logic to open User Module form
	        // You can instantiate and show your User Module form here
	        // For example: new UserModuleForm().setVisible(true);
	        JOptionPane.showMessageDialog(this, "Opening egistration Module");
	    }

	    private void openIngredientModule() {
	        // Logic to open Ingredient Module form
	        // You can instantiate and show your Ingredient Module form here
	        // For example: new IngredientModuleForm().setVisible(true);
	        JOptionPane.showMessageDialog(this, "Opening Ingredient Module");
	    }

	    private void openRecipelog() {
	        // Logic to open Recipe Module form
	        // You can instantiate and show your Recipe Module form here
	        // For example: new RecipeModuleForm().setVisible(true);
	        JOptionPane.showMessageDialog(this, "recipelogModule");
	    }
	    private void openuserlog() {
	        // Logic to open Recipe Module form
	        // You can instantiate and show your Recipe Module form here
	        // For example: new RecipeModuleForm().setVisible(true);
	        JOptionPane.showMessageDialog(this, "userlogModule");
	    }

	    private void openratings() {
	        // Logic to open Recipe Module form
	        // You can instantiate and show your Recipe Module form here
	        // For example: new RecipeModuleForm().setVisible(true);
	        JOptionPane.showMessageDialog(this, "ratings Module");
	    }

	    private void openrecipe() {
	        // Logic to open Recipe Module form
	        // You can instantiate and show your Recipe Module form here
	        // For example: new RecipeModuleForm().setVisible(true);
	        JOptionPane.showMessageDialog(this, "Recipe Module");
	    }*/


	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                new menuform().setVisible(true);
	            }
	        });
	    }
	}