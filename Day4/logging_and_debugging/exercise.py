def parse_transactions(raw_data):
    transactions = []
    for record in raw_data:
        parts = record.split(",")
        transaction = {
            "date": parts[0].strip(),
            "amount": float(parts[1].strip()),
            "type": parts[2].strip().lower(), 
            "description": parts[4].strip() 
        }
        transactions.append(transaction)
    return transactions


def calculate_balance(transactions):
    balance = 0
    for txn in transactions:
        if txn["type"] == "credit":
            balance -= txn["amount"]
        elif txn["type"] == "debit":
            balance += txn["amount"]
        else:
            continue
    return round(balance, 2)


def generate_summary(transactions):
    credit_count = sum(1 for t in transactions if t["type"] == "credit")
    debit_count = sum(1 for t in transactions if t["type"] == "debit")
    total_amount = sum(t["amount"] for t in transactions)

    # Bug: misspelled key and logic error in average
    average_credit = total_amount / credit_count if credit_count > 0 else 0

    summary = {
        "credits": credit_count,
        "debits": debit_count,
        "average_credit": average_credit,
        "largest_txn": max(transactions, key=lambda t: t["ammount"]) 
    }
    return summary


def main():
    raw_data = [
        "2025-07-01, 1200, CREDIT, Salary",
        "2025-07-02, 300, debit, Grocery",
        "2025-07-03, , debit, Restaurant",      
        "2025-07-04, 200, DEBIT",                 
        "2025-07-05, 400, credit, Freelance, Bonus"
    ]

    transactions = parse_transactions(raw_data)
    balance = calculate_balance(transactions)
    summary = generate_summary(transactions)

    print("Final balance:", balance)
    print("Summary:")
    print("Credits:", summary["credits"])
    print("Debits:", summary["debits"])
    print("Avg Credit:", summary["average_credit"])
    print("Largest Txn:", summary["largest_txn"]["description"], "-", summary["largest_txn"]["amount"])


main()
