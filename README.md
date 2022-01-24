# Dragon-Ball-Adventures
A Dragon Ball themed turned based RPG

## [Showcase Video](https://drive.google.com/file/d/0B-7LF1y06lxEVU1mN1Z2T21SMEE/view?resourcekey=0-r7TFiQ33TgX0UjV4h2132Q)

## Best GUI 4th Place Winner

# Game Design Document 
## Game Overview
The game is a role-playing game (RPG) based on Dragon Ball. It consists of both a world exploration mode where the player navigates the a map as well as a battle mode. The possible actions and characteristics depends on the chosen type of fighter.

## Player Characteristics
* The player chooses a fighter to play as (role)
* Fighters have the following attributes
  * Name
  * Level
  * Experience
  * Target Experience
  * Ability Points
* The strength of a fighter is further determined by the following attributes
  * Health Points
  * Blast Damage
  * Physical Damage
  * Ki
  * Stamina
* Fighters belong to one of 5 races
  * Earthling
  * Saiyan
  * Namekian
  * Frieza’s race
  * Majin
* The initial strength of fighters differs from one race to another. The initial values for the strength attributes of each race are shown in the following table

Race | Max Health Points | Blast Damage | Physical Damage | Max Ki | Max Stamina 
------------ | ------------ | ------------ | ------------ | ------------ | ------------ 
Earthling| 1250|50|50|4|4
Saiyan|1000|150|100|5|3
Namekian|1350|0|50|3|5
Frieza’s race|1100|75|75|4|4
Majin|1500|50|50|3|6

## Gameplay
The gameplay is divided into two interchanging modes: ​
*world exploration* and *battle*. ​There is a third special mode, *Dragon*, ​which is only activated in case of collection of 7 dragon balls.

### World Exploration
The player navigates a 10x10 grid map which is randomly generated, according to the following:
* Active fighter: bottom right of the map (visible)
* Boss: top left of the map (visible)
* Weak Foes: Set number randomly positioned in map (invisible)
* Senzu Beans: Random number randomly positioned in map (invisible)
* Dragon Ball: One ball randomly positioned in map (invisible)

The player is allowed to move in any direction, 1 cell at a time and can randomly encounter:
* Collectibles (Consequence: ​The player collects this collectible):
  * Dragon ball
  * Senzu bean
* Fightables (Consequence: ​go to battle mode):
  * Normal foe
  * Boss (Strong foe)

During the World Mode players can:
*  Create a new fighter.
*  Switch the active fighter.
*  Assign Super and Ultimate Attacks to their fighters from the list of unlocked attacks; up to 4 Super Attacks and 2 Ultimate Attacks per fighter. Note that a player can replace one of their fighter’s attack with another one from the unlocked list; meaning an assigned attack could be changed later on.
* Upgrade his fighters’ attributes using the available ability points. One ability point could be
spent on one of these options:
  * Increase Max Health Points by 50 points.
  * Increase Physical Damage by 50 points.
  * Increase Blast Damage by 50 points.
  * Increase Max Ki by one bar.
  * Increase Max Stamina by one bar.

### Battle

Once a fighter hits a cell containing a fightable the game mode automatically changes to battle. In a battle there is the fighter and his opponent; the fighter’s objective is to win the battle and gain xp.  
Whenever the battle starts, each fighter’s health points and current stamina bars are set to the maximum while the current Ki bars are set to 0.

#### Actions possible during a battle:
* Attack
  * Physical Attack  
Damage = 50 + fighter’s physical damage  
Using the physical attack charges 1 Ki bar
  * Super Attack  
Damage: Specific attack damage + fighter’s blast damage  
Usually requires and consumes: 1 ki bar  
There is a special type of super attack called Maximum Charge. This attack’s damage is zero, and ​doesn’t require nor consume any Ki bars. Its effect is that it charges
3 Ki bars (i.e. 3 empty Ki bars become full).
  * Ultimate Attack  
  Damage: Specific attack damage + blast damage  
  Usually requires and consumes: 3 ki bars  
  There is a special type of ultimate attack called Super Saiyan. This attack’s damage is zero, and ​
requires but does not consume 3 Ki bars and it can only be used by fighters whose race is Saiyan. Its effect is that the Saiyan fighter becomes transformed. When a Saiyan is transformed into a Super Saiyan he/she does not require nor any Ki for performing super or ultimate attacks. Additionally, the damage inflicted by them is increased by 25%. This extra power comes at the cost of losing 1 Ki bar per turn. Once the Ki becomes 0 then the transformation state ends and the fighter’s stamina becomes 0.

  Each super and ultimate attack has a name and a damage value which will be provided in
a file containing all possible attacks in the game.

* Block  
Blocks the opponent’s next turn attack  
Fighters can choose to use their stamina to block attacks. For each 100 damage 1 stamina bar will be consumed and the HP will remain the same. If stamina reaches zero the remaining unblocked damage will be inflicted on the fighter’s HP.
* Use item  
The fighter can use a Senzu bean that is collected by the player in the world mode restore health and stamina to maximum.

#### Winning a Battle
In case of winning the battle (opponent’s HP reaches zero)
* gain XP, according to the opponent’s level
* unlock foe’s super and ultimate attacks i.e. skills
* gain 2 ability points, in case the gained xp causes the fighter to level up
* unlock new map, in case the opponent was a boss

#### XP rules
Gained XP = foe’s level * 5.

Whenever a fighter’s xp has reached the target XP, he/she levels up (level increases by 1) and gains 2 ability points. The new target XP will then be increased by 20 and the XP will be reset to 0.

#### Default actions per turn
Regardless of the action performed by the fighter during his/her turn, there are some race-specific actions that happen by default each turn. These actions are mainly concerned with regenerating some of the fighter’s attributes

Race | Default Actions
-----| -------
Earthling | +1 stamina/turn<br/>+1 ki/my turn
Saiyan | +1 stamina/turn <br/> **If transformed:** -1 Ki/turn. If Ki becomes 0 then the transformation state ends and stamina becomes 0
Namekian| +1 stamina/turn<br/>+50 health/turn
Frieza’s race| +1 stamina/turn
Majin | +1 stamina/foe turn
**Note:** ​
The opponent’s actions per turn during battle are randomly chosen without any AI considerations

### Dragon
Once a player has gathered 7 dragon balls, the dragon appears to grant him one wish. The player can choose between
* senzu beans
* ability points
* unlocking a new random super attack
* unlocking a new random ultimate attack

## Game flow
* The player should be able to create and choose between different characters to start a game
* For the gameplay to be possible the game needs to have state saving capabilities. The game contents alongside the player’s current characteristics and belongings need to
be saved each time the game is closed.  
Upon ​starting the game the player should be able to start a new game or continue a saved
one.
* The game starts in the world mode and switches to the battle mode only for the duration of a battle. After the battle the player is returned to the world mode.
* The Dragon mode is only activated in case of gathering 7 dragon balls. After choosing a wish the player is returned to the world mode and the collected Dragon Balls are reset to 0.
* The game play can theoretically run forever. There is no ​
game over ​state.
***

# Project Structure
The project was split into 4 milestones

[M1] ​Hierarchy: All Data Structures + Roles  
[M2] Engine: World, Battle and Dragon Modes  
[M3] Exceptions + State Saving  
[M4] GUI + Integration 

The output of each is on a separate branch. As a different version of the previous milestone was provided and used for the following milestone for consistency with other teams.

**Note:** All images were removed for copyright reasons
***
Done as a part of Computer Programming Lab course.
