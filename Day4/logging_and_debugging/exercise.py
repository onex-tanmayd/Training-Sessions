import logging

logging.basicConfig(
    level=logging.DEBUG,
    format='%(asctime)s - %(levelname)s - %(message)s',
    datefmt='%Y-%m-%d %H:%M:%S'
)

logger = logging.getLogger(__name__)

def parse_transactions(raw_data):
    transactions = []
    logger.info(f"Starting to parse {len(raw_data)} transaction records")
    
    for i, record in enumerate(raw_data):
        logger.debug(f"Processing record {i+1}: {record}")
        
        parts = record.split(",")
        logger.debug(f"Split into {len(parts)} parts: {parts}")
        
        # Handle missing amount - originaly this would crash with ValueError
        try:
            amount_str = parts[1].strip()
            if not amount_str:  # Empty amount field
                logger.warning(f"Empty amount in record {i+1}, skipping transaction")
                continue
            amount = float(amount_str)
        except (ValueError, IndexError) as e:
            logger.error(f"Invalid amount in record {i+1}: {e}")
            continue
            
        # Handle missing description field - the original code assumed 5 parts but some have only 4
        try:
            if len(parts) >= 5:
                description = parts[4].strip() 
            elif len(parts) >= 4:
                description = parts[3].strip() if parts[3].strip() else "No description"
            else:
                description = "No description"
                logger.warning(f"Missing description in record {i+1}, using default")
        except IndexError:
            description = "No description"
            logger.warning(f"Could not extract description from record {i+1}")
            
        transaction = {
            "date": parts[0].strip(),
            "amount": amount,
            "type": parts[2].strip().lower(), 
            "description": description
        }
        transactions.append(transaction)
        logger.debug(f"Successfully parsed transaction: {transaction}")
        
    logger.info(f"Successfully parsed {len(transactions)} valid transactions")
    return transactions

def calculate_balance(transactions):
    balance = 0
    logger.info("Calculating account balance")
    
    for txn in transactions:
        # Fixed the logic - credits should ADD money, debits should SUBTRACT
        # The orignal code had it opposite due to which the balance was wrong
        if txn["type"] == "credit":
            balance += txn["amount"]  # Credits ++
            logger.debug(f"Credit of {txn['amount']}: balance now {balance}")
        elif txn["type"] == "debit":
            balance -= txn["amount"]  # Debits --
            logger.debug(f"Debit of {txn['amount']}: balance now {balance}")
        else:
            logger.warning(f"Unknown transaction type: {txn['type']}")
            continue
            
    final_balance = round(balance, 2)
    logger.info(f"Final calculated balance: {final_balance}")
    return final_balance

def generate_summary(transactions):
    logger.info("Generating transaction summary")
    
    credit_count = sum(1 for t in transactions if t["type"] == "credit")
    debit_count = sum(1 for t in transactions if t["type"] == "debit")
    
    # Fixed calculation - should only include credit amounts, not all amounts
    credit_total = sum(t["amount"] for t in transactions if t["type"] == "credit")
    average_credit = credit_total / credit_count if credit_count > 0 else 0
    
    logger.debug(f"Credits: {credit_count}, Debits: {debit_count}")
    logger.debug(f"Credit total: {credit_total}, Average credit: {average_credit}")
    
    # Fixed the typo in key name - was "ammount" instead of "amount"
    # This woud have caused a KeyError when trying to find the largest transaction
    try:
        largest_txn = max(transactions, key=lambda t: t["amount"])  # Fixed: was t["ammount"]
        logger.debug(f"Largest transaction: {largest_txn}")
    except ValueError:
        logger.error("No transactions available to find largest")
        largest_txn = {"amount": 0, "description": "None"}
    
    summary = {
        "credits": credit_count,
        "debits": debit_count,
        "average_credit": round(average_credit, 2),
        "largest_txn": largest_txn
    }
    
    logger.info(f"Summary generated: {summary}")
    return summary

def main():
    logger.info("Starting transaction processing application")
    
    raw_data = [
        "2025-07-01, 1200, CREDIT, Salary",
        "2025-07-02, 300, debit, Grocery",
        "2025-07-03, , debit, Restaurant",      # Empty amount - will be skipped
        "2025-07-04, 200, DEBIT",                 # Missing description
        "2025-07-05, 400, credit, Freelance, Bonus"
    ]
    
    try:
        transactions = parse_transactions(raw_data)
        balance = calculate_balance(transactions)
        summary = generate_summary(transactions)
        
        print("\n" + "="*50)
        print("TRANSACTION PROCESSING RESULTS")
        print("="*50)
        print(f"Final balance: ${balance}")
        print("\nSummary:")
        print(f"Credits: {summary['credits']}")
        print(f"Debits: {summary['debits']}")
        print(f"Avg Credit: ${summary['average_credit']}")
        
        # Handle the case where largest transaction might not have description
        largest = summary["largest_txn"]
        desc = largest.get("description", "No description")
        print(f"Largest Txn: {desc} - ${largest['amount']}")
        
        logger.info("Transaction processing completed successfully")
        
    except Exception as e:
        logger.error(f"Fatal error during processing: {e}")
        print(f"Error: {e}")

if __name__ == "__main__":
    main()
