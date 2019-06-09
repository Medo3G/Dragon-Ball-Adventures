package dragonball.controller.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;

import dragonball.model.attack.*;
import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleEvent;
import dragonball.model.battle.BattleEventType;
import dragonball.model.cell.Collectible;
import dragonball.model.cell.FoeCell;
import dragonball.model.character.fighter.Earthling;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.Frieza;
import dragonball.model.character.fighter.Majin;
import dragonball.model.character.fighter.Namekian;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.dragon.Dragon;
import dragonball.model.dragon.DragonWish;
import dragonball.model.exceptions.*;
import dragonball.model.game.*;
import dragonball.view.*;

@SuppressWarnings("serial")
public class GameGUI implements GameListener, ActionListener, KeyListener, java.io.Serializable {

	private GameWindow gameWindow;
	private Game game;
	private DragonWish[] currentDragonWishes;
	private Battle currentBattle;
	private int initialBattleLevel;

	public GameGUI() {
		gameWindow = new GameWindow();
		TitleScreen t = new TitleScreen();
		t.getNewGame().addActionListener(this);
		t.getLoadGame().addActionListener(this);
		gameWindow.setContentPane(t);

		gameWindow.pack();
		gameWindow.repaint();
		gameWindow.validate();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() instanceof AbstractButton) {
			String name = ((AbstractButton) arg0.getSource()).getName();

			if (name.equals("newGame")) {
				NewGameScreen n = new NewGameScreen();
				n.getOk().addActionListener(this);
				n.getCancel().addActionListener(this);
				for (int i = 0; i < n.getRaces().size(); i++) {
					n.getRaces().get(i).addActionListener(this);

				}
				gameWindow.setContentPane(n);
				gameWindow.pack();
				gameWindow.repaint();
				gameWindow.validate();
			} else if (name.equals("newGameOK")) {
				try {
					game = new Game();
					NewGameScreen n = (NewGameScreen) gameWindow.getContentPane();
					String playerName = n.getPlayerInput().getText();
					String fighterName = n.getFighterInput().getText();

					String race = "";

					for (int i = 0; i < n.getRaces().size(); i++) {
						if (n.getRaces().get(i).isSelected()) {
							race = n.getRaces().get(i).getName();
							break;
						}
					}
					game.getPlayer().setName(playerName);
					if (race.equals("earthling"))
						game.getPlayer().createFighter('E', fighterName);
					else if (race.equals("saiyan"))
						game.getPlayer().createFighter('S', fighterName);
					else if (race.equals("namekian"))
						game.getPlayer().createFighter('N', fighterName);
					else if (race.equals("frieza"))
						game.getPlayer().createFighter('F', fighterName);
					else if (race.equals("majin"))
						game.getPlayer().createFighter('M', fighterName);
					game.setListener(this);
					WorldScreen w = new WorldScreen();
					fillWorldScreen(w);

					gameWindow.setContentPane(w);
					gameWindow.pack();
					// w.getMap().setFocusable(true);
					w.addKeyListener(this);
					w.requestFocusInWindow();
					gameWindow.repaint();
					gameWindow.validate();

				} catch (MissingFieldException | UnknownAttackTypeException e) {
				}

			} else if (name.equals("loadGame")) {
				LoadGameScreen l = new LoadGameScreen();
				l.getOk().addActionListener(this);
				l.getCancel().addActionListener(this);
				File folder = new File(System.getProperty("user.dir") + "/saves");
				if (folder.exists()) {
					File[] listOfFiles = folder.listFiles();
					ButtonGroup bg = new ButtonGroup();
					for (int i = 0; i < listOfFiles.length; i++) {
						GenericToggleButton r = new GenericToggleButton(listOfFiles[i].getName());
						r.setMaximumSize(new Dimension(500, 40));
						r.setMinimumSize(new Dimension(500, 40));
						if (i == 0)
							r.setSelected(true);
						bg.add(r);
						l.getRadioButtons().add(r);
						l.getSaves().add(r);
					}
					if (listOfFiles.length == 0) {
						JLabel noSaves = new JLabel("No saves found");
						noSaves.setForeground(Color.white);
						noSaves.setMaximumSize(new Dimension(500, 600));
						noSaves.setHorizontalAlignment(SwingConstants.CENTER);
						l.getSaves().add(noSaves);
					}
				} else {
					JLabel noSaves = new JLabel("No saves found");
					noSaves.setForeground(Color.white);
					noSaves.setMaximumSize(new Dimension(500, 600));
					noSaves.setHorizontalAlignment(SwingConstants.CENTER);
					l.getSaves().add(noSaves);

				}

				gameWindow.setContentPane(l);
				gameWindow.pack();
				gameWindow.repaint();
				gameWindow.validate();
			}

			else if (name.equals("newGameCancel") || name.equals("loadGameCancel")) {
				TitleScreen t = new TitleScreen();
				t.getNewGame().addActionListener(this);
				t.getLoadGame().addActionListener(this);
				gameWindow.setContentPane(t);

				gameWindow.pack();
				gameWindow.repaint();
				gameWindow.validate();
			}

			else if (name.equals("loadGameOK")) {
				LoadGameScreen l = (LoadGameScreen) gameWindow.getContentPane();
				String save = "";
				for (int i = 0; i < l.getRadioButtons().size(); i++) {
					if (l.getRadioButtons().get(i).isSelected()) {
						save = l.getRadioButtons().get(i).getText();
						break;
					}

				}
				try {
					game = new Game();
					game.load(System.getProperty("user.dir") + File.separator + "saves" + File.separator + save);
					game.setListener(this);

					WorldScreen w = new WorldScreen();
					fillWorldScreen(w);

					gameWindow.setContentPane(w);
					gameWindow.pack();
					w.addKeyListener(this);
					w.requestFocusInWindow();
					gameWindow.repaint();
					gameWindow.validate();

				} catch (ClassNotFoundException | IOException e) {
					DialogBox d = new DialogBox(new String[] { "Loading Failed." });
					d.getOk().addActionListener(this);
					l.add(d, 0);
				}
			} else if (name.equals("createFighter")) {
				CreateFighterScreen c = new CreateFighterScreen();
				c.getOk().addActionListener(this);
				c.getCancel().addActionListener(this);
				for (int i = 0; i < c.getRaces().size(); i++) {
					c.getRaces().get(i).addActionListener(this);

				}
				gameWindow.setContentPane(c);
				gameWindow.pack();
				gameWindow.repaint();
				gameWindow.validate();
			} else if (name.equals("createFighterOK")) {

				CreateFighterScreen c = (CreateFighterScreen) gameWindow.getContentPane();

				String fighterName = c.getFighterInput().getText();

				String race = "";

				for (int i = 0; i < c.getRaces().size(); i++) {
					if (c.getRaces().get(i).isSelected()) {
						race = c.getRaces().get(i).getName();
						break;
					}
				}
				if (race.equals("earthling"))
					game.getPlayer().createFighter('E', fighterName);
				else if (race.equals("saiyan"))
					game.getPlayer().createFighter('S', fighterName);
				else if (race.equals("namekian"))
					game.getPlayer().createFighter('N', fighterName);
				else if (race.equals("frieza"))
					game.getPlayer().createFighter('F', fighterName);
				else if (race.equals("majin"))
					game.getPlayer().createFighter('M', fighterName);
				game.setListener(this);
				WorldScreen w = new WorldScreen();
				fillWorldScreen(w);

				gameWindow.setContentPane(w);
				gameWindow.pack();
				w.addKeyListener(this);
				w.requestFocusInWindow();
				gameWindow.repaint();
				gameWindow.validate();

			}

			else if (name.equals("switchFighter")) {
				SwitchFighterScreen s = new SwitchFighterScreen();
				s.getOk().addActionListener(this);
				s.getCancel().addActionListener(this);
				ButtonGroup bg = new ButtonGroup();
				for (int i = 0; i < game.getPlayer().getFighters().size(); i++) {
					GenericToggleButton r = new GenericToggleButton(game.getPlayer().getFighters().get(i).getName());

					if (game.getPlayer().getFighters().get(i) == game.getPlayer().getActiveFighter())
						r.setSelected(true);
					r.setMaximumSize(new Dimension(500, 40));
					r.setMinimumSize(new Dimension(500, 40));
					bg.add(r);
					s.getRadioButtons().add(r);
					s.getFighters().add(r);
				}
				gameWindow.setContentPane(s);
				gameWindow.pack();
				gameWindow.repaint();
				gameWindow.validate();

			}

			else if (name.equals("switchFighterOK")) {
				SwitchFighterScreen s = (SwitchFighterScreen) gameWindow.getContentPane();
				for (int i = 0; i < s.getRadioButtons().size(); i++) {
					if (s.getRadioButtons().get(i).isSelected()) {
						game.getPlayer().setActiveFighter(game.getPlayer().getFighters().get(i));
						break;
					}

				}
				WorldScreen w = new WorldScreen();
				fillWorldScreen(w);

				gameWindow.setContentPane(w);
				gameWindow.pack();
				w.addKeyListener(this);
				w.requestFocusInWindow();
				gameWindow.repaint();
				gameWindow.validate();
			}

			else if (name.equals("assignAttacks")) {
				AssignAttacksScreen a = new AssignAttacksScreen();
				a.getOk().addActionListener(this);
				a.getAssignSuper().addActionListener(this);
				a.getAssignUlt().addActionListener(this);
				a.getSuperSwitch().addActionListener(this);
				a.getUltSwitch().addActionListener(this);
				ButtonGroup bg1 = new ButtonGroup();
				for (int i = 0; i < game.getPlayer().getSuperAttacks().size(); i++) {
					GenericToggleButton s = new GenericToggleButton(game.getPlayer().getSuperAttacks().get(i).getName()
							+ " (" + game.getPlayer().getSuperAttacks().get(i).getDamage() + ")");
					if (i == 0) {
						s.setSelected(true);
					}
					s.setMaximumSize(new Dimension(400, 40));
					s.setMinimumSize(new Dimension(400, 40));
					bg1.add(s);
					a.getSupers().add(s);
					a.getAllSupers().add(s);

				}
				if (a.getSupers().size() == 0) {
					JLabel noSupers = new JLabel("You have no Super attacks");
					noSupers.setForeground(Color.white);
					noSupers.setHorizontalAlignment(SwingConstants.CENTER);
					noSupers.setMaximumSize(new Dimension(400, 500));
					noSupers.setMinimumSize(new Dimension(400, 500));
					a.getAllSupers().add(noSupers);
					a.getAssignSuper().setVisible(false);
				}

				ButtonGroup bg2 = new ButtonGroup();
				for (int i = 0; i < 4; i++) {
					GenericToggleButton sp = new GenericToggleButton("Empty");
					if (i < game.getPlayer().getActiveFighter().getSuperAttacks().size()) {
						sp.setText(game.getPlayer().getActiveFighter().getSuperAttacks().get(i).getName() + " ("
								+ game.getPlayer().getActiveFighter().getSuperAttacks().get(i).getDamage() + ")");
					}
					if (i == 0)
						sp.setSelected(true);
					sp.setMaximumSize(new Dimension(500, 40));
					sp.setMinimumSize(new Dimension(500, 40));
					bg2.add(sp);
					a.getPlayerSupers().add(sp);
					a.getPlayerSupersPanel().add(sp);

				}
				gameWindow.setContentPane(a);
				gameWindow.pack();
				gameWindow.repaint();
				gameWindow.validate();

			} else if (name.equals("superSwitch")) {
				AssignAttacksScreen a = (AssignAttacksScreen) gameWindow.getContentPane();
				a.getSuperPanel().setVisible(true);
				a.getUltPanel().setVisible(false);

				gameWindow.repaint();
				gameWindow.validate();
			} else if (name.equals("ultSwitch")) {
				AssignAttacksScreen a = (AssignAttacksScreen) gameWindow.getContentPane();
				if (a.getPlayerUlts().size() == 0) {
					ButtonGroup bg1 = new ButtonGroup();
					for (int i = 0; i < game.getPlayer().getUltimateAttacks().size(); i++) {
						GenericToggleButton u = new GenericToggleButton(
								game.getPlayer().getUltimateAttacks().get(i).getName() + " ("
										+ game.getPlayer().getUltimateAttacks().get(i).getDamage() + ")");
						if (i == 0) {
							u.setSelected(true);
						}
						u.setMaximumSize(new Dimension(500, 40));
						u.setMinimumSize(new Dimension(500, 40));
						bg1.add(u);
						u.setMaximumSize(new Dimension(400, 40));
						u.setMinimumSize(new Dimension(400, 40));
						a.getUlts().add(u);
						a.getAllUlts().add(u);

					}
					if (a.getUlts().size() == 0) {
						JLabel noUlts = new JLabel("You have no Ultimate attacks");
						noUlts.setForeground(Color.white);
						noUlts.setHorizontalAlignment(SwingConstants.CENTER);
						noUlts.setMaximumSize(new Dimension(400, 500));
						noUlts.setMinimumSize(new Dimension(400, 500));
						a.getAllUlts().add(noUlts);
						a.getAssignUlt().setVisible(false);
					}

					ButtonGroup bg2 = new ButtonGroup();
					for (int i = 0; i < 2; i++) {
						GenericToggleButton ut = new GenericToggleButton("Empty");
						if (i < game.getPlayer().getActiveFighter().getUltimateAttacks().size()) {
							ut.setText(game.getPlayer().getActiveFighter().getUltimateAttacks().get(i).getName() + " ("
									+ game.getPlayer().getActiveFighter().getUltimateAttacks().get(i).getDamage()
									+ ")");
						}
						if (i == 0)
							ut.setSelected(true);
						ut.setMaximumSize(new Dimension(500, 40));
						ut.setMinimumSize(new Dimension(500, 40));
						bg2.add(ut);
						a.getPlayerUlts().add(ut);
						a.getPlayerUltsPanel().add(ut);

					}

				}

				a.getSuperPanel().setVisible(false);
				a.getUltPanel().setVisible(true);

				gameWindow.repaint();
				gameWindow.validate();
			}

			else if (name.equals("assignAttacksOK") || name.equals("upgradeFighterOK") || name.equals("saveCancel")
					|| name.equals("createFighterCancel") || name.equals("switchFighterCancel")
					|| name.equals("battleEndOK")) {

				WorldScreen w = new WorldScreen();
				fillWorldScreen(w);

				gameWindow.setContentPane(w);
				gameWindow.pack();
				w.addKeyListener(this);
				w.requestFocusInWindow();
				gameWindow.repaint();
				gameWindow.validate();

			}

			else if (name.equals("assignSuper")) {
				AssignAttacksScreen a = (AssignAttacksScreen) gameWindow.getContentPane();

				SuperAttack sToAssign = null;
				for (int i = 0; i < a.getSupers().size(); i++) {
					if (a.getSupers().get(i).isSelected()) {
						sToAssign = game.getPlayer().getSuperAttacks().get(i);
						break;
					}
				}
				SuperAttack sToReplace = null;
				for (int i = 0; i < a.getPlayerSupers().size(); i++) {
					if (a.getPlayerSupers().get(i).isSelected()) {
						if (!a.getPlayerSupers().get(i).getText().equals("Empty"))
							sToReplace = game.getPlayer().getActiveFighter().getSuperAttacks().get(i);
						break;
					}
				}
				try {
					game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(), sToAssign, sToReplace);
					ButtonGroup bg2 = new ButtonGroup();
					a.getPlayerSupersPanel().removeAll();

					a.getPlayerSupers().clear();

					for (int i = 0; i < 4; i++) {
						GenericToggleButton sp = new GenericToggleButton("Empty");
						if (i < game.getPlayer().getActiveFighter().getSuperAttacks().size()) {
							sp.setText(game.getPlayer().getActiveFighter().getSuperAttacks().get(i).getName() + " ("
									+ game.getPlayer().getActiveFighter().getSuperAttacks().get(i).getDamage() + ")");
						}
						if (i == 0)
							sp.setSelected(true);
						sp.setMaximumSize(new Dimension(500, 40));
						sp.setMinimumSize(new Dimension(500, 40));
						bg2.add(sp);
						a.getPlayerSupers().add(sp);
						a.getPlayerSupersPanel().add(sp);
						gameWindow.repaint();
						gameWindow.validate();

					}
				} catch (InvalidAssignAttackException e) {
					DialogBox d = new DialogBox(new String[] { e.getMessage() });
					d.getOk().addActionListener(this);
					a.add(d, 0);
					a.setLayer(d, JLayeredPane.MODAL_LAYER);
				}

			} else if (name.equals("assignUlt")) {
				AssignAttacksScreen a = (AssignAttacksScreen) gameWindow.getContentPane();

				UltimateAttack uToAssign = null;
				for (int i = 0; i < a.getUlts().size(); i++) {
					if (a.getUlts().get(i).isSelected()) {
						uToAssign = game.getPlayer().getUltimateAttacks().get(i);
						break;
					}
				}
				UltimateAttack uToReplace = null;
				for (int i = 0; i < a.getPlayerSupers().size(); i++) {
					if (a.getPlayerUlts().get(i).isSelected()) {
						if (!a.getPlayerUlts().get(i).getText().equals("Empty"))
							uToReplace = game.getPlayer().getActiveFighter().getUltimateAttacks().get(i);
						break;
					}
				}
				try {
					game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(), uToAssign, uToReplace);
					ButtonGroup bg2 = new ButtonGroup();
					a.getPlayerUltsPanel().removeAll();
					a.getPlayerUlts().clear();

					for (int i = 0; i < 2; i++) {
						GenericToggleButton ut = new GenericToggleButton("Empty");
						if (i < game.getPlayer().getActiveFighter().getUltimateAttacks().size()) {
							ut.setText(game.getPlayer().getActiveFighter().getUltimateAttacks().get(i).getName() + " ("
									+ game.getPlayer().getActiveFighter().getUltimateAttacks().get(i).getDamage()
									+ ")");
						}
						if (i == 0)
							ut.setSelected(true);
						ut.setMaximumSize(new Dimension(500, 40));
						ut.setMinimumSize(new Dimension(500, 40));
						bg2.add(ut);
						a.getPlayerUlts().add(ut);
						a.getPlayerUltsPanel().add(ut);
						gameWindow.repaint();
						gameWindow.validate();

					}
				} catch (InvalidAssignAttackException e) {
					DialogBox d = new DialogBox(new String[] { e.getMessage() });
					d.getOk().addActionListener(this);
					a.add(d, 0);
					a.setLayer(d, JLayeredPane.MODAL_LAYER);
				}

			} else if (name.equals("dialogBoxOk")) {
				JLayeredPane j = (JLayeredPane) gameWindow.getContentPane();
				j.remove(0);
				gameWindow.pack();
				if (j instanceof WorldScreen) {
					j.setFocusable(true);
					j.requestFocusInWindow();
				}

				gameWindow.repaint();
				gameWindow.validate();

			}

			else if (name.equals("upgradeFighter")) {
				UpgradeFighterScreen u = new UpgradeFighterScreen();

				u.getOk().addActionListener(this);
				u.getUpgradeHP().addActionListener(this);
				u.getUpgradeKi().addActionListener(this);
				u.getUpgradeStamina().addActionListener(this);
				u.getUpgradePhysical().addActionListener(this);
				u.getUpgradeBlast().addActionListener(this);

				u.getFighterName().setText("Name: " + game.getPlayer().getActiveFighter().getName());
				u.getAbilityPoints()
						.setText("Ability Points: " + game.getPlayer().getActiveFighter().getAbilityPoints());
				u.getMaxHP().setText("Max HP: " + game.getPlayer().getActiveFighter().getMaxHealthPoints());
				u.getMaxKi().setText("Max Ki: " + game.getPlayer().getActiveFighter().getMaxKi());
				u.getMaxStamina().setText("Max Stamina: " + game.getPlayer().getActiveFighter().getMaxStamina());
				u.getPhysicalDamage()
						.setText("Physical Damage: " + game.getPlayer().getActiveFighter().getPhysicalDamage());
				u.getBlastDamage().setText("Blast Damage: " + game.getPlayer().getActiveFighter().getBlastDamage());

				gameWindow.setContentPane(u);
				gameWindow.pack();
				gameWindow.repaint();
				gameWindow.validate();

			}

			else if (name.equals("upgradeHP")) {
				UpgradeFighterScreen u = (UpgradeFighterScreen) gameWindow.getContentPane();

				try {
					game.getPlayer().upgradeFighter(game.getPlayer().getActiveFighter(), 'H');
					u.getAbilityPoints()
							.setText("Ability Points: " + game.getPlayer().getActiveFighter().getAbilityPoints());
					u.getMaxHP().setText("Max HP: " + game.getPlayer().getActiveFighter().getMaxHealthPoints());

				} catch (NotEnoughAbilityPointsException e) {
					DialogBox d = new DialogBox(new String[] { e.getMessage() });
					d.getOk().addActionListener(this);
					u.add(d, 0);

				}
			}

			else if (name.equals("upgradeKi")) {
				UpgradeFighterScreen u = (UpgradeFighterScreen) gameWindow.getContentPane();

				try {
					game.getPlayer().upgradeFighter(game.getPlayer().getActiveFighter(), 'K');
					u.getAbilityPoints()
							.setText("Ability Points: " + game.getPlayer().getActiveFighter().getAbilityPoints());
					u.getMaxKi().setText("Max Ki: " + game.getPlayer().getActiveFighter().getMaxKi());

				} catch (NotEnoughAbilityPointsException e) {
					DialogBox d = new DialogBox(new String[] { e.getMessage() });
					d.getOk().addActionListener(this);
					gameWindow.getRootPane().setDefaultButton(d.getOk());
					d.getOk().requestFocusInWindow();
					u.add(d, 0);
					u.setLayer(d, JLayeredPane.MODAL_LAYER);

				}
			}

			else if (name.equals("upgradeStamina")) {
				UpgradeFighterScreen u = (UpgradeFighterScreen) gameWindow.getContentPane();

				try {
					game.getPlayer().upgradeFighter(game.getPlayer().getActiveFighter(), 'S');
					u.getAbilityPoints()
							.setText("Ability Points: " + game.getPlayer().getActiveFighter().getAbilityPoints());
					u.getMaxStamina().setText("Max Stamina: " + game.getPlayer().getActiveFighter().getMaxStamina());

				} catch (NotEnoughAbilityPointsException e) {
					DialogBox d = new DialogBox(new String[] { e.getMessage() });
					d.getOk().addActionListener(this);
					u.add(d, 0);

				}
			}

			else if (name.equals("upgradePhysical")) {
				UpgradeFighterScreen u = (UpgradeFighterScreen) gameWindow.getContentPane();

				try {
					game.getPlayer().upgradeFighter(game.getPlayer().getActiveFighter(), 'P');
					u.getAbilityPoints()
							.setText("Ability Points: " + game.getPlayer().getActiveFighter().getAbilityPoints());
					u.getPhysicalDamage()
							.setText("Physical Damage: " + game.getPlayer().getActiveFighter().getPhysicalDamage());

				} catch (NotEnoughAbilityPointsException e) {
					DialogBox d = new DialogBox(new String[] { e.getMessage() });
					d.getOk().addActionListener(this);
					u.add(d, 0);

				}
			}

			else if (name.equals("upgradeBlast")) {
				UpgradeFighterScreen u = (UpgradeFighterScreen) gameWindow.getContentPane();
				try {
					game.getPlayer().upgradeFighter(game.getPlayer().getActiveFighter(), 'B');
					u.getAbilityPoints()
							.setText("Ability Points: " + game.getPlayer().getActiveFighter().getAbilityPoints());
					u.getBlastDamage().setText("Blast Damage: " + game.getPlayer().getActiveFighter().getBlastDamage());

				} catch (NotEnoughAbilityPointsException e) {
					DialogBox d = new DialogBox(new String[] { e.getMessage() });
					d.getOk().addActionListener(this);
					u.add(d, 0);

				}
			}

			else if (name.equals("save")) {
				SaveScreen s = new SaveScreen();
				s.getOk().addActionListener(this);
				s.getCancel().addActionListener(this);

				gameWindow.setContentPane(s);
				gameWindow.pack();
				gameWindow.repaint();
				gameWindow.validate();

			}

			else if (name.equals("saveOK")) {
				SaveScreen s = (SaveScreen) gameWindow.getContentPane();
				String filename = s.getSave().getText();
				File folder = new File(System.getProperty("user.dir") + "/saves");
				if (!folder.exists()) {
					folder.mkdirs();
				}
				try {
					game.save(System.getProperty("user.dir") + File.separator + "saves" + File.separator + filename);
					DialogBox d = new DialogBox(new String[] { "Saving successful." });
					d.getOk().addActionListener(this);
					s.add(d, 0);

				} catch (IOException e) {
					DialogBox d = new DialogBox(new String[] { "Saving failed." });
					d.getOk().addActionListener(this);
					s.add(d, 0);
				}

			} else if (name.equals("dragonOK")) {
				DragonScreen d = (DragonScreen) gameWindow.getContentPane();
				DragonWish wish = null;
				for (int i = 0; i < d.getWishes().length; i++) {
					if (d.getWishes()[i].isSelected()) {

						wish = currentDragonWishes[i];
						game.getPlayer().chooseWish(wish);
						WorldScreen w = new WorldScreen();
						fillWorldScreen(w);

						gameWindow.setContentPane(w);
						gameWindow.pack();
						w.addKeyListener(this);
						w.requestFocusInWindow();
						gameWindow.repaint();
						gameWindow.validate();
						break;
					}
				}
				DialogBox dia = new DialogBox(new String[] { "You must choose a wish" });
				dia.getOk().addActionListener(this);
				d.add(dia, 0);

			}

			else if (name.equals("attack")) {
				BattleScreen b = (BattleScreen) gameWindow.getContentPane();

				b.getAttacksContainer().setVisible(!b.getAttacksContainer().isVisible());
				gameWindow.pack();
				gameWindow.repaint();
				gameWindow.validate();
			}

			else if (name.equals("block")) {
				BattleScreen b = (BattleScreen) gameWindow.getContentPane();
				b.getAttacksContainer().setVisible(false);
				b.getAttack().setVisible(false);
				b.getBlock().setVisible(false);
				b.getUse().setVisible(false);

				gameWindow.pack();
				gameWindow.repaint();
				gameWindow.validate();
				currentBattle.block();
			}

			else if (name.equals("use")) {
				BattleScreen b = (BattleScreen) gameWindow.getContentPane();
				b.getAttacksContainer().setVisible(false);
				try {

					b.getAttack().setVisible(false);
					b.getBlock().setVisible(false);
					b.getUse().setVisible(false);
					gameWindow.pack();
					gameWindow.repaint();
					gameWindow.validate();
					currentBattle.use(game.getPlayer(), Collectible.SENZU_BEAN);
				} catch (NotEnoughSenzuBeansException e) {

					b.getAttack().setVisible(true);
					b.getBlock().setVisible(true);
					b.getUse().setVisible(true);
					DialogBox d = new DialogBox(new String[] { e.getMessage() });
					d.getOk().addActionListener(this);
					b.add(d, 0);
					gameWindow.pack();
					gameWindow.repaint();
					gameWindow.validate();
				}

			} else if (name.equals("doAttack")) {
				BattleScreen b = (BattleScreen) gameWindow.getContentPane();
				b.getAttacksContainer().setVisible(false);

				try {
					for (int i = 0; i < b.getAttackButtons().size(); i++) {
						if (((GenericButton) arg0.getSource()) == b.getAttackButtons().get(i)) {

							b.getAttack().setVisible(false);
							b.getBlock().setVisible(false);
							b.getUse().setVisible(false);
							gameWindow.pack();
							gameWindow.repaint();
							gameWindow.validate();
							currentBattle.attack(currentBattle.getAssignedAttacks().get(i));
							break;
						}

					}
				} catch (NotEnoughKiException e) {
					b.getAttack().setVisible(true);
					b.getBlock().setVisible(true);
					b.getUse().setVisible(true);
					DialogBox d = new DialogBox(new String[] { e.getMessage() });
					d.getOk().addActionListener(this);
					b.add(d, 0);
					gameWindow.pack();
					gameWindow.repaint();
					gameWindow.validate();
				}

			}

			else if (name.equals("earthling")) {
				if (gameWindow.getContentPane() instanceof NewGameScreen) {
					NewGameScreen n = (NewGameScreen) gameWindow.getContentPane();
					n.getRaceBio().setText(n.getBios()[0]);

				} else if (gameWindow.getContentPane() instanceof CreateFighterScreen) {
					CreateFighterScreen c = (CreateFighterScreen) gameWindow.getContentPane();
					c.getRaceBio().setText(c.getBios()[0]);

				}
			} else if (name.equals("saiyan")) {
				if (gameWindow.getContentPane() instanceof NewGameScreen) {
					NewGameScreen n = (NewGameScreen) gameWindow.getContentPane();
					n.getRaceBio().setText(n.getBios()[1]);

				} else if (gameWindow.getContentPane() instanceof CreateFighterScreen) {
					CreateFighterScreen c = (CreateFighterScreen) gameWindow.getContentPane();
					c.getRaceBio().setText(c.getBios()[1]);

				}
			} else if (name.equals("namekian")) {
				if (gameWindow.getContentPane() instanceof NewGameScreen) {
					NewGameScreen n = (NewGameScreen) gameWindow.getContentPane();
					n.getRaceBio().setText(n.getBios()[2]);

				} else if (gameWindow.getContentPane() instanceof CreateFighterScreen) {
					CreateFighterScreen c = (CreateFighterScreen) gameWindow.getContentPane();
					c.getRaceBio().setText(c.getBios()[2]);

				}
			} else if (name.equals("frieza")) {
				if (gameWindow.getContentPane() instanceof NewGameScreen) {
					NewGameScreen n = (NewGameScreen) gameWindow.getContentPane();
					n.getRaceBio().setText(n.getBios()[3]);

				} else if (gameWindow.getContentPane() instanceof CreateFighterScreen) {
					CreateFighterScreen c = (CreateFighterScreen) gameWindow.getContentPane();
					c.getRaceBio().setText(c.getBios()[3]);

				}
			} else if (name.equals("majin")) {
				if (gameWindow.getContentPane() instanceof NewGameScreen) {
					NewGameScreen n = (NewGameScreen) gameWindow.getContentPane();
					n.getRaceBio().setText(n.getBios()[4]);

				} else if (gameWindow.getContentPane() instanceof CreateFighterScreen) {
					CreateFighterScreen c = (CreateFighterScreen) gameWindow.getContentPane();
					c.getRaceBio().setText(c.getBios()[4]);

				}
			}
		}

	}

	public void fillWorldScreen(WorldScreen w) {

		w.getPlayerName().setText(game.getPlayer().getName());
		w.getDragonballs().setText(game.getPlayer().getDragonBalls() + "");
		w.getSenzubeans().setText(game.getPlayer().getSenzuBeans() + "");
		w.getExploredMaps().setText(game.getPlayer().getExploredMaps() + "");

		populateMap(w);

		if (game.getPlayer().getActiveFighter() instanceof Saiyan) {
			w.getRaceImage().setIcon(new ImageIcon("images/world/Saiyan.gif"));
		} else if (game.getPlayer().getActiveFighter() instanceof Earthling) {
			w.getRaceImage().setIcon(new ImageIcon("images/world/Earthling.gif"));

		} else if (game.getPlayer().getActiveFighter() instanceof Majin) {
			w.getRaceImage().setIcon(new ImageIcon("images/world/Majin.gif"));

		} else if (game.getPlayer().getActiveFighter() instanceof Frieza) {
			w.getRaceImage().setIcon(new ImageIcon("images/world/Frieza.gif"));

		} else if (game.getPlayer().getActiveFighter() instanceof Namekian) {
			w.getRaceImage().setIcon(new ImageIcon("images/world/Namekian.gif"));

		}

		w.getFighterName().setText(game.getPlayer().getActiveFighter().getName());
		w.getFighterLevel().setText("LVL: " + game.getPlayer().getActiveFighter().getLevel());
		w.getXPBar().removeAll();
		w.getXPBar().add(new XPBar(game.getPlayer().getActiveFighter().getXp(),
				game.getPlayer().getActiveFighter().getTargetXp()));
		w.getMaxHP().setText("Max HP: " + game.getPlayer().getActiveFighter().getMaxHealthPoints());
		w.getMaxKi().setText("Max Ki: " + game.getPlayer().getActiveFighter().getMaxKi());
		w.getMaxStamina().setText("Max Stamina: " + game.getPlayer().getActiveFighter().getMaxStamina());
		w.getPhysicalDamage().setText("Physical Damage: " + game.getPlayer().getActiveFighter().getPhysicalDamage());
		w.getBlastDamage().setText("Blast Damage: " + game.getPlayer().getActiveFighter().getBlastDamage());

		for (int i = 0; i < w.getButtons().size(); i++) {
			w.getButtons().get(i).addActionListener(this);
		}

	}

	public void populateMap(WorldScreen w) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (i == game.getWorld().getPlayerRow() && j == game.getWorld().getPlayerColumn()) {
					JLabel y = new JLabel();
					// y.setBackground(new Color(255, 255, 255, 200));
					// y.setOpaque(true);
					y.setHorizontalAlignment(SwingConstants.CENTER);
					y.setBorder(BorderFactory.createLineBorder(Color.white));
					if (game.getPlayer().getActiveFighter() instanceof Saiyan) {
						y.setIcon(new ImageIcon("images/world/icons/Saiyan.png"));
					} else if (game.getPlayer().getActiveFighter() instanceof Earthling) {
						y.setIcon(new ImageIcon("images/world/icons/Earthling.png"));

					} else if (game.getPlayer().getActiveFighter() instanceof Majin) {
						y.setIcon(new ImageIcon("images/world/icons/Majin.png"));

					} else if (game.getPlayer().getActiveFighter() instanceof Frieza) {
						y.setIcon(new ImageIcon("images/world/icons/Frieza.png"));

					} else if (game.getPlayer().getActiveFighter() instanceof Namekian) {
						y.setIcon(new ImageIcon("images/world/icons/Namekian.png"));

					}
					w.getMap().add(y);
				} else if (game.getWorld().getMap()[i][j] instanceof FoeCell
						&& ((NonPlayableFighter) ((FoeCell) game.getWorld().getMap()[i][j]).getFoe()).isStrong()) {
					JLabel y = new JLabel(
							((NonPlayableFighter) ((FoeCell) game.getWorld().getMap()[i][j]).getFoe()).getName());
					y.setBackground(new Color(255, 255, 255, 200));
					y.setOpaque(true);
					y.setForeground(Color.red);
					y.setHorizontalAlignment(SwingConstants.CENTER);
					y.setBorder(BorderFactory.createLineBorder(Color.white));
					w.getMap().add(y); // TODO will be image
				} else {
					JLabel y = new JLabel("");
					y.setBorder(BorderFactory.createLineBorder(Color.white));
					w.getMap().add(y);
				}

			}

		}
	}

	@Override
	public void onDragonCalled(Dragon dragon) {
		DragonScreen d = new DragonScreen();
		currentDragonWishes = dragon.getWishes();
		d.getDragonImage().setIcon(new ImageIcon("images/dragon/sprites/" + dragon.getName() + ".png"));
		d.getOk().addActionListener(this);
		d.getDragonName().setText(dragon.getName());
		d.getWishes()[0].setText(currentDragonWishes[0].getSenzuBeans() + " Senzu Beans");
		d.getWishes()[1].setText(currentDragonWishes[1].getAbilityPoints() + " Ability Points");
		d.getWishes()[2].setText("Super Attack: " + currentDragonWishes[2].getSuperAttack().getName() + " ("
				+ currentDragonWishes[2].getSuperAttack().getDamage() + ")");
		d.getWishes()[3].setText("Ultimate Attack: " + currentDragonWishes[3].getUltimateAttack().getName() + " ("
				+ currentDragonWishes[3].getUltimateAttack().getDamage() + ")");

		gameWindow.setContentPane(d);
		gameWindow.pack();
		gameWindow.repaint();
		gameWindow.validate();
	}

	@Override
	public void onCollectibleFound(Collectible collectible) {
		WorldScreen w = (WorldScreen) gameWindow.getContentPane();
		String[] text = new String[1];
		if (collectible == Collectible.SENZU_BEAN) {
			text[0] = "You have found a Senzu Bean!";
			w.getSenzubeans().setText(game.getPlayer().getSenzuBeans() + "");
		} else if (collectible == Collectible.DRAGON_BALL) {
			text[0] = "You have found a Dragon Ball!";
			w.getDragonballs().setText(game.getPlayer().getDragonBalls() + "");
		}
		DialogBox d = new DialogBox(text);
		d.getOk().addActionListener(this);
		w.setFocusable(false);
		w.add(d, 0);

	}

	@Override
	public void onBattleEvent(BattleEvent e) {
		BattleEventType type = e.getType();
		if (type == BattleEventType.STARTED) {
			NonPlayableFighter foe = (NonPlayableFighter) ((Battle) e.getSource()).getFoe();
			PlayableFighter me = (PlayableFighter) ((Battle) e.getSource()).getMe();

			BattleScreen b = new BattleScreen();
			b.getAttack().addActionListener(this);
			b.getBlock().addActionListener(this);
			b.getUse().addActionListener(this);
			b.getBackgroundImage().setIcon(new ImageIcon("images/battle/bg/" + new Random().nextInt(10) + ".gif"));

			if (me instanceof Saiyan) {
				b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Saiyan/Stand.gif"));
			} else if (me instanceof Earthling) {
				b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Earthling/Stand.gif"));

			} else if (me instanceof Majin) {
				b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Majin/Stand.gif"));

			} else if (me instanceof Frieza) {
				b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Frieza/Stand.gif"));

			} else if (me instanceof Namekian) {
				b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Namekian/Stand.gif"));

			}

			b.getFoeSprite().setIcon(new ImageIcon("images/battle/fighters/" + foe.getName() + "/Stand.gif"));
			currentBattle = (Battle) e.getSource();
			initialBattleLevel = game.getPlayer().getActiveFighter().getLevel();

			fillBattleStats(b);

			ArrayList<Attack> attacks = currentBattle.getAssignedAttacks();

			for (int i = 0; i < 7; i++) {
				if (i < attacks.size()) {
					GenericButton a = new GenericButton(attacks.get(i).getName());
					a.setPreferredSize(new Dimension(250, 30));
					a.setMaximumSize(new Dimension(250, 30));
					a.setName("doAttack");
					a.addActionListener(this);
					b.getAttackButtons().add(a);
					b.getAttacksContainer().add(a);
				} else {
					b.getAttacksContainer().add(new JLabel());
				}

			}

			gameWindow.setContentPane(b);
			gameWindow.pack();
			gameWindow.repaint();
			gameWindow.validate();
		}

		else if (type == BattleEventType.ENDED) {

			NonPlayableFighter foe = (NonPlayableFighter) ((Battle) e.getSource()).getFoe();
			PlayableFighter me = (PlayableFighter) ((Battle) e.getSource()).getMe();
			ArrayList<String> textList = new ArrayList<>();

			Timer timer = new Timer();

			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					if (currentBattle != null && gameWindow.getContentPane() instanceof BattleScreen) {
						BattleScreen b = (BattleScreen) gameWindow.getContentPane();

						if (e.getWinner() == currentBattle.getMe()) {
							if (me instanceof Saiyan) {
								if (((Saiyan) me).isTransformed())
									b.getMeSprite().setIcon(
											new ImageIcon("images/battle/playable/Saiyan/Transformed/Stand.gif"));
								else
									b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Saiyan/Stand.gif"));
							} else if (me instanceof Earthling) {
								b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Earthling/Stand.gif"));

							} else if (me instanceof Majin) {
								b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Majin/Stand.gif"));

							} else if (me instanceof Frieza) {
								b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Frieza/Stand.gif"));

							} else if (me instanceof Namekian) {
								b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Namekian/Stand.gif"));

							}

							b.getFoeSprite()
									.setIcon(new ImageIcon("images/battle/fighters/" + foe.getName() + "/Dead.gif"));

						} else if (e.getWinner() == currentBattle.getFoe()) {
							if (me instanceof Saiyan) {
								if (((Saiyan) me).isTransformed())
									b.getMeSprite().setIcon(
											new ImageIcon("images/battle/playable/Saiyan/Transformed/Dead.gif"));
								else
									b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Saiyan/Dead.gif"));
							} else if (me instanceof Earthling) {
								b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Earthling/Dead.gif"));

							} else if (me instanceof Majin) {
								b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Majin/Dead.gif"));

							} else if (me instanceof Frieza) {
								b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Frieza/Dead.gif"));

							} else if (me instanceof Namekian) {
								b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Namekian/Dead.gif"));

							}

							b.getFoeSprite()
									.setIcon(new ImageIcon("images/battle/fighters/" + foe.getName() + "/Stand.gif"));

						}
						currentBattle = null;
					}

				}
			}, 1500);

			if (currentBattle != null && gameWindow.getContentPane() instanceof BattleScreen) {
				BattleScreen b = (BattleScreen) gameWindow.getContentPane();

				if (e.getWinner() == currentBattle.getMe()) {
					fillBattleStats(b);

					if (foe.isStrong()) {
						textList.add("Boss defeated.");
						textList.add("You have unlocked a new map.");

					} else {
						textList.add("Weak foe defeated.");

					}
					textList.add("You have defeated " + foe.getName());
					ArrayList<String> attacks = new ArrayList<>();
					for (int i = 0; i < foe.getSuperAttacks().size(); i++) {
						attacks.add(foe.getSuperAttacks().get(i).getName());
					}
					for (int i = 0; i < foe.getUltimateAttacks().size(); i++) {
						attacks.add(foe.getUltimateAttacks().get(i).getName());
					}
					if (attacks.size() > 0) {
						textList.add("You have unlocked the following attacks:");
						textList.addAll(attacks);
					}
					if (me.getLevel() > initialBattleLevel) {
						textList.add("You have reached level " + me.getLevel() + ".");
						textList.add("You now have " + me.getAbilityPoints() + " ability points.");
						textList.add("The XP required to reach the next level is " + me.getTargetXp() + ".");
					}
					textList.add("You now have " + me.getXp() + "XP.");

				} else if (e.getWinner() == currentBattle.getFoe()) {

					textList.add("You died.");
					textList.add(foe.getName() + " has defeated you.");
				}

			} else {
				textList.add("You died.");
				textList.add(foe.getName() + " has defeated you.");
				WorldScreen w = new WorldScreen();
				fillWorldScreen(w);
				gameWindow.getContentPane().removeAll();
				gameWindow.setContentPane(w);
				gameWindow.pack();
				w.addKeyListener(this);
				w.requestFocusInWindow();
				gameWindow.repaint();
				gameWindow.validate();

			}

			String[] text = null;
			text = textList.toArray(new String[] {});
			DialogBox d = new DialogBox(text);
			d.getOk().setName("battleEndOK");
			d.getOk().addActionListener(this);

			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					gameWindow.getContentPane().add(d, 0);
					gameWindow.pack();
					gameWindow.repaint();
					gameWindow.validate();
				}
			}, 1500);

		}

		else if (type == BattleEventType.NEW_TURN) {

			Timer timer = new Timer();

			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					BattleScreen b = (BattleScreen) gameWindow.getContentPane();
					fillBattleStats(b);
					b.getActionText().setVisible(false);

					NonPlayableFighter foe = (NonPlayableFighter) ((Battle) e.getSource()).getFoe();
					PlayableFighter me = (PlayableFighter) ((Battle) e.getSource()).getMe();

					if (currentBattle.isMeBlocking()) {
						if (me instanceof Saiyan) {
							if (((Saiyan) me).isTransformed())
								b.getMeSprite()
										.setIcon(new ImageIcon("images/battle/playable/Saiyan/Transformed/Block.gif"));
							else
								b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Saiyan/Block.gif"));
						} else if (me instanceof Earthling) {
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Earthling/Block.gif"));

						} else if (me instanceof Majin) {
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Majin/Block.gif"));

						} else if (me instanceof Frieza) {
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Frieza/Block.gif"));

						} else if (me instanceof Namekian) {
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Namekian/Block.gif"));

						}

					} else {
						if (me instanceof Saiyan) {
							if (((Saiyan) me).isTransformed())
								b.getMeSprite()
										.setIcon(new ImageIcon("images/battle/playable/Saiyan/Transformed/Stand.gif"));
							else
								b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Saiyan/Stand.gif"));
						} else if (me instanceof Earthling) {
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Earthling/Stand.gif"));

						} else if (me instanceof Majin) {
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Majin/Stand.gif"));

						} else if (me instanceof Frieza) {
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Frieza/Stand.gif"));

						} else if (me instanceof Namekian) {
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Namekian/Stand.gif"));

						}
					}
					if (currentBattle.isFoeBlocking()) {
						b.getFoeSprite()
								.setIcon(new ImageIcon("images/battle/fighters/" + foe.getName() + "/Block.gif"));
					} else {
						b.getFoeSprite()
								.setIcon(new ImageIcon("images/battle/fighters/" + foe.getName() + "/Stand.gif"));
					}

					if (currentBattle.getAttacker() instanceof NonPlayableFighter) {
						b.getAttack().setVisible(false);
						b.getBlock().setVisible(false);
						b.getUse().setVisible(false);
						gameWindow.pack();
						gameWindow.repaint();
						gameWindow.validate();
						currentBattle.play();
					} else {
						b.getAttack().setVisible(true);
						b.getBlock().setVisible(true);
						b.getUse().setVisible(true);
						gameWindow.pack();
						gameWindow.repaint();
						gameWindow.validate();
					}

				}
			}, 1500);
		}

		else if (type == BattleEventType.ATTACK) {
			BattleScreen b = (BattleScreen) gameWindow.getContentPane();
			fillBattleStats(b);
			b.getActionText().setText(
					((Fighter) currentBattle.getAttacker()).getName() + " used " + e.getAttack().getName() + ".");
			b.getActionText().setVisible(true);

			NonPlayableFighter foe = (NonPlayableFighter) ((Battle) e.getSource()).getFoe();
			PlayableFighter me = (PlayableFighter) ((Battle) e.getSource()).getMe();

			if (currentBattle.getAttacker() == me) {
				if (e.getAttack() instanceof SuperSaiyan) {
					if (((Saiyan) me).isTransformed())
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Saiyan/Transforming.gif"));
				}

				else if (e.getAttack() instanceof MaximumCharge) {
					if (me instanceof Saiyan) {
						if (((Saiyan) me).isTransformed())
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Saiyan/Transformed/Max.gif"));
						else
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Saiyan/Max.gif"));
					} else if (me instanceof Earthling) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Earthling/Max.gif"));

					} else if (me instanceof Majin) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Majin/Max.gif"));

					} else if (me instanceof Frieza) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Frieza/Max.gif"));

					} else if (me instanceof Namekian) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Namekian/Max.gif"));

					}

				} else if (e.getAttack() instanceof PhysicalAttack) {
					if (me instanceof Saiyan) {
						if (((Saiyan) me).isTransformed())
							b.getMeSprite()
									.setIcon(new ImageIcon("images/battle/playable/Saiyan/Transformed/Physical.gif"));
						else
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Saiyan/Physical.gif"));
					} else if (me instanceof Earthling) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Earthling/Physical.gif"));

					} else if (me instanceof Majin) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Majin/Physical.gif"));

					} else if (me instanceof Frieza) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Frieza/Physical.gif"));

					} else if (me instanceof Namekian) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Namekian/Physical.gif"));

					}

				}

				else if (e.getAttack() instanceof SuperAttack) {
					if (me instanceof Saiyan) {
						if (((Saiyan) me).isTransformed())
							b.getMeSprite()
									.setIcon(new ImageIcon("images/battle/playable/Saiyan/Transformed/Super.gif"));
						else
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Saiyan/Super.gif"));
					} else if (me instanceof Earthling) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Earthling/Super.gif"));

					} else if (me instanceof Majin) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Majin/Super.gif"));

					} else if (me instanceof Frieza) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Frieza/Super.gif"));

					} else if (me instanceof Namekian) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Namekian/Super.gif"));

					}

				}

				else if (e.getAttack() instanceof UltimateAttack) {
					if (me instanceof Saiyan) {
						if (((Saiyan) me).isTransformed())
							b.getMeSprite()
									.setIcon(new ImageIcon("images/battle/playable/Saiyan/Transformed/Ultimate.gif"));
						else
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Saiyan/Ultimate.gif"));
					} else if (me instanceof Earthling) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Earthling/Ultimate.gif"));

					} else if (me instanceof Majin) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Majin/Ultimate.gif"));

					} else if (me instanceof Frieza) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Frieza/Ultimate.gif"));

					} else if (me instanceof Namekian) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Namekian/Ultimate.gif"));

					}

				}
				if (currentBattle.isFoeBlocking()) {
					b.getFoeSprite().setIcon(new ImageIcon("images/battle/fighters/" + foe.getName() + "/Block.gif"));

				} else {
					if (e.getAttack() instanceof SuperSaiyan || e.getAttack() instanceof MaximumCharge) {
						b.getFoeSprite()
								.setIcon(new ImageIcon("images/battle/fighters/" + foe.getName() + "/Stand.gif"));

					} else
						b.getFoeSprite().setIcon(new ImageIcon("images/battle/fighters/" + foe.getName() + "/Hit.gif"));
				}

			} else {
				if (e.getAttack() instanceof SuperSaiyan) {
					b.getFoeSprite().setIcon(new ImageIcon("images/battle/fighters/" + foe.getName() + "/Stand.gif"));

				} else if (e.getAttack() instanceof MaximumCharge) {
					b.getFoeSprite().setIcon(new ImageIcon("images/battle/fighters/" + foe.getName() + "/Max.gif"));

				} else if (e.getAttack() instanceof PhysicalAttack) {
					b.getFoeSprite()
							.setIcon(new ImageIcon("images/battle/fighters/" + foe.getName() + "/Physical.gif"));

				} else if (e.getAttack() instanceof SuperAttack) {
					b.getFoeSprite().setIcon(new ImageIcon("images/battle/fighters/" + foe.getName() + "/Super.gif"));

				} else if (e.getAttack() instanceof UltimateAttack) {
					b.getFoeSprite()
							.setIcon(new ImageIcon("images/battle/fighters/" + foe.getName() + "/Ultimate.gif"));
				}

				if (currentBattle.isMeBlocking()) {
					if (me instanceof Saiyan) {
						if (((Saiyan) me).isTransformed())
							b.getMeSprite()
									.setIcon(new ImageIcon("images/battle/playable/Saiyan/Transformed/Block.gif"));
						else
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Saiyan/Block.gif"));
					} else if (me instanceof Earthling) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Earthling/Block.gif"));

					} else if (me instanceof Majin) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Majin/Block.gif"));

					} else if (me instanceof Frieza) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Frieza/Block.gif"));

					} else if (me instanceof Namekian) {
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Namekian/Block.gif"));

					}

				} else {
					if (e.getAttack() instanceof SuperSaiyan || e.getAttack() instanceof MaximumCharge) {
						if (me instanceof Saiyan) {
							if (((Saiyan) me).isTransformed())
								b.getMeSprite()
										.setIcon(new ImageIcon("images/battle/playable/Saiyan/Transformed/Stand.gif"));
							else
								b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Saiyan/Stand.gif"));
						} else if (me instanceof Earthling) {
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Earthling/Stand.gif"));

						} else if (me instanceof Majin) {
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Majin/Stand.gif"));

						} else if (me instanceof Frieza) {
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Frieza/Stand.gif"));

						} else if (me instanceof Namekian) {
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Namekian/Stand.gif"));

						}
					} else {

						if (me instanceof Saiyan) {
							if (((Saiyan) me).isTransformed())
								b.getMeSprite()
										.setIcon(new ImageIcon("images/battle/playable/Saiyan/Transformed/Hit.gif"));
							else
								b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Saiyan/Hit.gif"));
						} else if (me instanceof Earthling) {
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Earthling/Hit.gif"));

						} else if (me instanceof Majin) {
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Majin/Hit.gif"));

						} else if (me instanceof Frieza) {
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Frieza/Hit.gif"));

						} else if (me instanceof Namekian) {
							b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Namekian/Hit.gif"));

						}
					}

				}

			}

			gameWindow.pack();
			gameWindow.repaint();
			gameWindow.validate();

		}

		else if (type == BattleEventType.BLOCK) {
			BattleScreen b = (BattleScreen) gameWindow.getContentPane();
			fillBattleStats(b);
			b.getActionText().setText(((Fighter) currentBattle.getAttacker()).getName() + " used Block.");
			b.getActionText().setVisible(true);

			NonPlayableFighter foe = (NonPlayableFighter) ((Battle) e.getSource()).getFoe();
			PlayableFighter me = (PlayableFighter) ((Battle) e.getSource()).getMe();

			if (currentBattle.getAttacker() == me) {
				if (me instanceof Saiyan) {
					if (((Saiyan) me).isTransformed())
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Saiyan/Transformed/Block.gif"));
					else
						b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Saiyan/Block.gif"));
				} else if (me instanceof Earthling) {
					b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Earthling/Block.gif"));

				} else if (me instanceof Majin) {
					b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Majin/Block.gif"));

				} else if (me instanceof Frieza) {
					b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Frieza/Block.gif"));

				} else if (me instanceof Namekian) {
					b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Namekian/Block.gif"));

				}

			} else {

				b.getFoeSprite().setIcon(new ImageIcon("images/battle/fighters/" + foe.getName() + "/Block.gif"));
			}

			gameWindow.pack();
			gameWindow.repaint();
			gameWindow.validate();

		}

		else if (type == BattleEventType.USE) {
			BattleScreen b = (BattleScreen) gameWindow.getContentPane();
			fillBattleStats(b);
			b.getActionText().setText(((Fighter) currentBattle.getAttacker()).getName() + " used a Senzu Bean.");
			b.getActionText().setVisible(true);
			PlayableFighter me = (PlayableFighter) ((Battle) e.getSource()).getMe();

			if (me instanceof Saiyan) {
				if (((Saiyan) me).isTransformed())
					b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Saiyan/Transformed/Use.gif"));
				else
					b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Saiyan/Use.gif"));
			} else if (me instanceof Earthling) {
				b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Earthling/Use.gif"));

			} else if (me instanceof Majin) {
				b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Majin/Use.gif"));

			} else if (me instanceof Frieza) {
				b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Frieza/Use.gif"));

			} else if (me instanceof Namekian) {
				b.getMeSprite().setIcon(new ImageIcon("images/battle/playable/Namekian/Use.gif"));

			}

			gameWindow.pack();
			gameWindow.repaint();
			gameWindow.validate();

		}

	}

	public void fillBattleStats(BattleScreen battleScreen) {

		PlayableFighter me = (PlayableFighter) currentBattle.getMe();
		NonPlayableFighter foe = (NonPlayableFighter) currentBattle.getFoe();

		battleScreen.getPlayerStats().removeAll();
		battleScreen.getFoeStats().removeAll();
		if (me instanceof Saiyan && ((Saiyan) me).isTransformed()) {
			battleScreen.getPlayerStats()
					.add(new BattleSaiyanStats(me.getName(), me.getLevel(), me.getHealthPoints(),
							me.getMaxHealthPoints(), me.getKi(), me.getMaxKi(), me.getStamina(), me.getMaxStamina(),
							me == currentBattle.getAttacker()));

		} else {
			battleScreen.getPlayerStats()
					.add(new BattlePlayerStats(me.getName(), me.getLevel(), me.getHealthPoints(),
							me.getMaxHealthPoints(), me.getKi(), me.getMaxKi(), me.getStamina(), me.getMaxStamina(),
							me == currentBattle.getAttacker()));
		}
		battleScreen.getFoeStats()
				.add(new BattleFoeStats(foe.getName(), foe.getLevel(), foe.getHealthPoints(), foe.getMaxHealthPoints(),
						foe.getKi(), foe.getMaxKi(), foe.getStamina(), foe.getMaxStamina(),
						foe == currentBattle.getAttacker()));

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// DO NOTHING

	}

	@Override
	public void keyReleased(KeyEvent e) {

		int keyCode = e.getKeyCode();
		WorldScreen w = (WorldScreen) gameWindow.getContentPane();
		if (keyCode == 38 || keyCode == 87) {
			try {
				game.getWorld().moveUp();
			} catch (MapIndexOutOfBoundsException e1) {

			}
			w.getMap().removeAll();
			populateMap(w);
			gameWindow.pack();
			gameWindow.repaint();
			gameWindow.validate();
		} else if (keyCode == 37 || keyCode == 65) {
			try {
				game.getWorld().moveLeft();
			} catch (MapIndexOutOfBoundsException e1) {

			}
			w.getMap().removeAll();
			populateMap(w);
			gameWindow.pack();
			gameWindow.repaint();
			gameWindow.validate();
		} else if (keyCode == 40 || keyCode == 83) {
			try {
				game.getWorld().moveDown();
			} catch (MapIndexOutOfBoundsException e1) {

			}
			w.getMap().removeAll();
			populateMap(w);
			gameWindow.pack();
			gameWindow.repaint();
			gameWindow.validate();
		} else if (keyCode == 39 || keyCode == 68) {
			try {
				game.getWorld().moveRight();
			} catch (MapIndexOutOfBoundsException e1) {

			}
			w.getMap().removeAll();
			populateMap(w);
			gameWindow.pack();
			gameWindow.repaint();
			gameWindow.validate();
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// DO NOTHING

	}

	public GameWindow getGameWindow() {
		return gameWindow;
	}

	public Game getGame() {
		return game;
	}

	public DragonWish[] getCurrentDragonWishes() {
		return currentDragonWishes;
	}

	public Battle getCurrentBattle() {
		return currentBattle;
	}

	public int getInitialBattleLevel() {
		return initialBattleLevel;
	}

	public static void main(String[] args) {
		new GameGUI();
	}

}
