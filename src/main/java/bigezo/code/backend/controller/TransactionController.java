package bigezo.code.backend.controller;

import bigezo.code.backend.model.Transaction;
import bigezo.code.backend.model.TransactionDto;
import bigezo.code.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<TransactionDto>> getAllTransactions(
            @RequestParam(name = "schoolAdminId") Long schoolAdminId) {
        List<TransactionDto> transactions = transactionService.getAllTransactions(schoolAdminId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<TransactionDto> getTransactionById(
            @RequestParam(name = "schoolAdminId") Long schoolAdminId,
            @PathVariable Long id) {
        return transactionService.getTransactionById(schoolAdminId, id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TransactionDto> createTransaction(
            @RequestParam(name = "schoolAdminId") Long schoolAdminId,
            @RequestBody Transaction transaction) {
        TransactionDto createdTransaction = transactionService.createTransaction(schoolAdminId, transaction);
        return ResponseEntity.ok(createdTransaction);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TransactionDto> updateTransaction(
            @RequestParam(name = "schoolAdminId") Long schoolAdminId,
            @PathVariable Long id,
            @RequestBody Transaction transaction) {
        TransactionDto updatedTransaction = transactionService.updateTransaction(schoolAdminId, id, transaction);
        return ResponseEntity.ok(updatedTransaction);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTransaction(
            @RequestParam(name = "schoolAdminId") Long schoolAdminId,
            @PathVariable Long id) {
        transactionService.deleteTransaction(schoolAdminId, id);
        return ResponseEntity.noContent().build();
    }
}