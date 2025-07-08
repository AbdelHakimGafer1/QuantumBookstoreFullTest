# 📚 Quantum Bookstore

**Quantum Bookstore** is a fully OOP-based Java project that simulates an online bookstore. It supports different types of books and operations like adding, buying, and removing outdated ones. The system is designed to be easily extensible and clean.

---

## 🔍 Features

- 📦 Multiple book types:
  - `PaperBook`: Has stock, can be shipped, includes weight.
  - `EBook`: Has a fileType and is sent via email.
  - `ShowcaseBook`: Demo book, not for sale.

- 🛠 Operations:
  - `addBook(Book book)`: Add any book to inventory.
  - `removeOutdatedBooks(int years)`: Remove books older than N years.
  - `buyBook(String isbn, int qty, String email, String address)`: Buy a book.

- 📤 External Service Simulation:
  - `ShippingService`: Simulates physical shipping.
  - `MailService`: Simulates sending eBooks via email.

---

## 🧱 Structure

- `abstract class Book`: Base for all book types.
- `PaperBook`, `EBook`, `ShowcaseBook`: Extend `Book` and override behavior.
- `Bookstore`: Holds inventory and handles main operations.
- `QuantumBookstoreFullTest`: Test class that demonstrates the system.

---

## ✅ Extensibility

The system follows **Open/Closed Principle**. To add a new book type, just extend the `Book` class and implement the required logic. No existing code needs to be modified.

---

## 🚀 Getting Started

1. Compile the code:
```bash
javac QuantumBookstoreFullTest.java
