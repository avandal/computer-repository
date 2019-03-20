package com.excilys.computer_database.view;

import static com.excilys.computer_database.util.Util.boxMessage;

import java.util.Optional;

import com.excilys.computer_database.persistence.ComputerDAO;
import com.excilys.computer_database.util.Util;

public class DeleteComputerPage extends Page {
	
	private ComputerDAO dao;

	public DeleteComputerPage() {
		dao = ComputerDAO.getInstance();
	}

	@Override
	public String show() {
		System.out.println(boxMessage("Please give a computer id ('abort' to abort')"));

		String input = this.scan.nextLine();

		return input;
	}

	@Override
	public Optional<Page> exec(String input) {
		if (input == null || input.equals("")) {
			System.out.println(boxMessage("Invalid input"));
			return Optional.of(new DeleteComputerPage());
		}

		if (input.equals("abort")) {
			System.out.println(boxMessage("[Aborted] " + BACK_MENU));
			return Optional.of(new MenuPage());
		}

		Optional<Integer> idInput = Util.parseInt(input);

		if (!idInput.isPresent()) {
			System.out.println(boxMessage("Invalid id: must be a number"));
			return Optional.of(new DeleteComputerPage());
		}

		if (idInput.get() <= 0) {
			System.out.println(boxMessage("Invalid id: must be > 0"));
			return Optional.of(new DeleteComputerPage());
		}

		int status = dao.deleteComputer(idInput.get());
		
		if (status == 1) {
			System.out.println(boxMessage("Computer successfully deleted"));
		} else {
			System.out.println(boxMessage("[Problem] Fail deleting computer"));
		}
		
		return Optional.of(new MenuPage());
	}

}
