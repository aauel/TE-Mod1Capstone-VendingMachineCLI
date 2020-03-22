# TE-Mod1Capstone-VendingMachineCLI

### Vending Machine Software
You’ve been asked to develop an application for the newest vending machine distributor, Umbrella Corp. They’ve released a new vending machine (Vendo-Matic 600) that is integrated with everyone’s bank accounts allowing customers to purchase products right from their computers for convenience sake. 

#### Menus and submenus: 
1. Display items 
2. Purchase items     
    1) Feed Money     
    2) Select Product     
    3) Finish Transaction 
3. Exit 
4. Sales Report** hidden menu item
    1) Get Sales Report
    2) Return to main menu
    
#### Stories and Acceptance Criteria:
1. As a vending machine owner, I need a way to update my vending machine with the correct inventory and so that it can show the correct items for purchase every time it's started.
    1. Given a file contains the list of items formatted correctly, when the vending machine is started, the inventory is created/updated with what items are in each slot based on the contents of the file. Each inventory item has a name, price, and type (beverage, candy, chips, gum).
    2. Given an item is in the inventory file, when the vending machine is started and the inventory is updated, then the slot indicated is filled with five of that product.

2. As a customer, I want to see items available for purchase in the vending machine.
    1. Given an item is available when a customer displays items, the item is displayed with the name, slot identifier and purchase price.
    2. Given an item is sold out or otherwise not available when a customer displays items, the item is displayed as SOLD OUT.

3. As a customer, I want to purchase items from the vending machine. 
    1. Given a customer is in the purchase menu, they are able to deposit money in whole dollar amounts.
    2. When they deposit money, his/her current balance is updated and displayed.
    3. Given a customer has not deposited money (current balance is $0) when they try to select a product, they are given an error message that they must deposit money before making a selection.
    4. When they select an item for purchase that is available: 
          1) the customer's current balance is updated based on item cost 
          2) the inventory and balance of the vending machine are updated 
          3) the item is dispensed and the user receives a message based on the item type:   
                - All chip items will print “Crunch Crunch, Yum!” 
                - All candy items will print “Munch Munch, Yum!” 
                - All drink items will print “Glug Glug, Yum!” 
                - All gum items will print “Chew Chew, Yum!”  
          4) the customer is returned to the purchase menu
    5. When the customer attempts to purchase an item that is sold out or otherwise not available, they are given an error message and returned to the purchase menu.
    6. When they choose Finish Transaction, the customer receives a message with their change using nickels, dimes, quarters and dollars using the smallest amount of coins possible and the current balance is updated to $0.

4. As a vending machine owner, I want an audit file so that I can see what my vending machine has sold.
    1. Then there should be an entry for every time money was fed in that includes date, time, feed money, amount, current balance.
    2. Then there should be an entry for every purchase that includes date, time, item name, item slot, item price, remaining balance.
    3. Then there should be an entry for every transaction completed labeled give change that includes date, time, amount of change, remaining balance (should be $0).
    
5. As a vending machine owner, I want a sales report as a hidden menu option so that I can see the total sales since the machine was started.
    1. When the sales report is run, there is a line for that product with the item name | total number of sale.
    2. Then at the end of the report is a blank line followed by **TOTAL SALES** $dollar amount indicating the gross sales from the vending machine.
