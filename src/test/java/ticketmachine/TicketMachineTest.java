package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
		// On vérifie que le prix affiché correspond au paramètre passé lors de
		// l'initialisation
		// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
		// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(10);
		machine.insertMoney(20);
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
		// Cas 1 : le ticket n'est pas imprimé si le montant inséré est insuffisant
	void doesNotPrintTicketIfInsufficientBalance() {
		machine.insertMoney(PRICE - 10);
		assertFalse(machine.printTicket(), "Le ticket ne doit pas être imprimé quand le solde est insuffisant");
	}

	@Test
		// Cas 2 : le ticket est imprimé uniquement si le montant inséré est suffisant
	void printsTicketIfSufficientBalance() {
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket(), "Le ticket doit être imprimé quand le solde est suffisant");
	}

	@Test
		// Cas 3 : la balance diminue du prix du ticket après son impression
	void balanceDecreasesByTicketPriceAfterPrint() {
		machine.insertMoney(PRICE + 10);
		machine.printTicket();
		assertEquals(10, machine.getBalance(), "La balance doit diminuer du montant du ticket après impression");
	}

	@Test
		// Cas 4 : le total est mis à jour après l'impression d'un ticket
	void totalIncreasesAfterPrintTicket() {
		machine.insertMoney(PRICE);
		assertEquals(0, machine.getTotal(), "Le total ne doit pas changer tant que le ticket n'est pas imprimé");
		machine.printTicket();
		assertEquals(PRICE, machine.getTotal(), "Le total doit être mis à jour après l'impression d'un ticket");
	}

	@Test
		// Cas 5 : refund() rend la totalité du solde actuel
	void refundReturnsCorrectBalance() {
		machine.insertMoney(30);
		assertEquals(30, machine.refund(), "Le remboursement doit être égal au solde actuel");
	}

	@Test
		// Cas 6 : refund() remet le solde à zéro
	void refundResetsBalanceToZero() {
		machine.insertMoney(30);
		machine.refund();
		assertEquals(0, machine.getBalance(), "Le solde doit être réinitialisé à zéro après un remboursement");
	}

	@Test
		// Cas 7 : on ne peut pas insérer un montant négatif dans la machine
	void cannotInsertNegativeAmount() {
		assertThrows(IllegalArgumentException.class, () -> {
			machine.insertMoney(-10);
		}, "L'insertion d'une somme négative doit lancer une exception");
	}

	@Test
		// Cas 8 : une machine avec un prix négatif ne peut pas être créée
	void cannotCreateMachineWithNegativeTicketPrice() {
		assertThrows(IllegalArgumentException.class, () -> {
			new TicketMachine(-50);
		}, "Créer une machine avec un prix de ticket négatif doit lancer une exception");
	}
}


