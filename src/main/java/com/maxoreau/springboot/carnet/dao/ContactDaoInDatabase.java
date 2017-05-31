package com.maxoreau.springboot.carnet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.maxoreau.springboot.carnet.models.Contact;

@Repository
public class ContactDaoInDatabase implements daoGenerique<Contact> {
	
	@Override
	public void create(Contact contact) {
		int contactId = -1;
		contactId = getId(contact);
		if (contactId != -1) {
			// le contact existe déjà donc on ne fait rien
			System.out.println("le contact ");
			System.out.println(contact.toString());
			System.out.println("existe déjà");
		} else { // le contact n'existe pas donc on peut le créer
			Connection connection = ConnectionDatabase.getConnectionDatabase().getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = connection.prepareStatement("INSERT INTO contacts (prenom, nom, numero) VALUES (?, ?, ?);");
				pstmt.setString(1, contact.getPrenom());
				pstmt.setString(2, contact.getNom());
				pstmt.setString(3, contact.getNumero());
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Statement close problem");
					}
				}
			}

		}
	}

	@Override
	public List<Contact> readByName(String string) {
		Connection connection = ConnectionDatabase.getConnectionDatabase().getConnection();
		PreparedStatement pstmt = null;
		List<Contact> contacts = new ArrayList<>();
		try {
			pstmt = connection.prepareStatement("SELECT * FROM contacts WHERE ((nom LIKE ?) or (prenom LIKE ?) or (numero LIKE ?)) ORDER BY nom;");
			pstmt.setString(1, ("%" + string + "%"));
			pstmt.setString(2, ("%" + string + "%"));
			pstmt.setString(3, ("%" + string + "%"));
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				contacts.add(new Contact( rs.getInt("contact_id"), rs.getString("prenom"), rs.getString("nom"), rs.getString("numero")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return contacts;
	}

	@Override
	public Contact readById(int id) {
		Connection connection = ConnectionDatabase.getConnectionDatabase().getConnection();
		PreparedStatement pstmt = null;
		Contact contact = new Contact();
		try {
			pstmt = connection.prepareStatement("SELECT * FROM contacts WHERE contact_id = ?;");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				contact.setContactId(rs.getInt("contact_id"));
				contact.setPrenom(rs.getString("prenom"));
				contact.setNom(rs.getString("nom"));
				contact.setNumero(rs.getString("numero"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return contact;
	}
	
	@Override
	public void update(Contact contact) {
		Connection connection = ConnectionDatabase.getConnectionDatabase().getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement("UPDATE contacts SET prenom = ?, nom = ?, numero = ? WHERE contact_id = ?;");
			pstmt.setString(1, contact.getPrenom());
			pstmt.setString(2, contact.getNom());
			pstmt.setString(3, contact.getNumero());
			pstmt.setInt(4, contact.getContactId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void delete(int	id) {
	
			Connection connection = ConnectionDatabase.getConnectionDatabase().getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = connection.prepareStatement("DELETE FROM contacts WHERE contact_id = ?;");
				pstmt.setInt(1, id);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

	}

	@Override
	public List<Contact> getAll() {
		List<Contact> contacts = new ArrayList<>();
		String requete = "SELECT * FROM carnetscontacts.contacts ORDER BY nom";
		Connection connection = ConnectionDatabase.getConnectionDatabase().getConnection();
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(requete);
			while (rs.next()) {
				contacts.add(new Contact(rs.getInt("contact_id"), rs.getString("prenom"), rs.getString("nom"), rs.getString("numero")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return contacts;
	}

	@Override
	public int getId(Contact contact) {
		// Initialisation de l'Id à -1 :
		int contactId = -1;
		Connection connection = ConnectionDatabase.getConnectionDatabase().getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement("SELECT contact_id FROM contacts WHERE (nom = ? AND prenom = ? AND numero = ?);");
			pstmt.setString(1, contact.getNom());
			pstmt.setString(2, contact.getPrenom());
			pstmt.setString(3, contact.getNumero());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				contactId = rs.getInt("contact_id");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return contactId;
	}

}
