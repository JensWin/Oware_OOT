package gameTests;

import game.Player;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;

public class PlayerTest
{
    Player playerNameTests;
    Player playerSpielsteineTests;
    Player playerToString;

    @Before
    public void setUp() throws Exception
    {
        Player player = new Player("TestyBoy");
        this.playerNameTests = player;

        Player player1 = new Player("SpielsteineTests");
        this.playerSpielsteineTests = player1;

        Player player2 = new Player("toStringTester");
        this.playerToString = player2;
    }
    
    @After
    public void teardown() throws Exception
    {
    	
    	this.playerNameTests = null;
    	this.playerSpielsteineTests  = null;
    	this.playerToString = null;    	
    }

    @Test
    public void testGetName() throws Exception
    {
        assertEquals(playerNameTests.getName(), "TestyBoy");
    }

    @Test
    public void testGetNameFail() throws Exception
    {
        assertEquals(playerNameTests.getName(), "TestyBoy");
        assertFalse(playerNameTests.getName().equals("WrongName"));
        assertTrue(playerNameTests.getName().equals("TestyBoy"));
    }

    @Test
    public void testGetNameWithNumbers() throws Exception
    {
        Player player1NameTests = new Player("Testy9");
        assertEquals(player1NameTests.getName(), "Testy9");
        assertFalse(player1NameTests.getName().equals("WrongName"));
        assertTrue(player1NameTests.getName().equals("Testy9"));
    }

    @Test(expected = NullPointerException.class)
    public void testGetNameEmpty() throws Exception
    {
        Player player = new Player(null);
        assertThat(player.getName(), null);

    }

    @Test
    public void testGetNameEmptyButNotNull() throws Exception
    {
        this.playerNameTests.setName("");
        assertEquals(playerNameTests.getName(), "");
        assertTrue(playerNameTests.getName().equals(""));
    }

    /**
     * Test set/getSpielsteine
     */

    @Test
    public void testGetSpielsteine() throws Exception
    {
        assertEquals(this.playerSpielsteineTests.getSpielSteineSpieler(), 0);
        assertTrue(this.playerSpielsteineTests.getSpielSteineSpieler() == 0);
        assertFalse(this.playerSpielsteineTests.getSpielSteineSpieler() != 0);
    }


    @Test
    public void testSetSpielsteine() throws Exception
    {
        playerSpielsteineTests.setSpielSteineSpieler(5);
        assertThat(playerSpielsteineTests.getSpielSteineSpieler(), is(5));
        assertTrue(playerSpielsteineTests.getSpielSteineSpieler() == 5);
        assertFalse(playerSpielsteineTests.getSpielSteineSpieler() != 5);
    }

    @Test
    public void testSetSpielsteineLess0() throws Exception
    {

        playerSpielsteineTests.setSpielSteineSpieler(-5);
        assertThat(playerSpielsteineTests.getSpielSteineSpieler(), is(-5));
        assertTrue(playerSpielsteineTests.getSpielSteineSpieler() == -5);
        assertFalse(playerSpielsteineTests.getSpielSteineSpieler() != -5);
    }

    @Test
    public void testSetSpielsteineTooBigNumber() throws Exception
    {
        playerSpielsteineTests.setSpielSteineSpieler(9999);
        assertThat(playerSpielsteineTests.getSpielSteineSpieler(), is(9999));
        assertTrue(playerSpielsteineTests.getSpielSteineSpieler() == 9999);
        assertFalse(playerSpielsteineTests.getSpielSteineSpieler() != 9999);
    }


    @Test
    public void setPlayerNameTestAllround() throws Exception
    {
        /**
         * Runden Simulation bei der, der Spieler Steine verliert, gewinnt und auf 0 gesetzt wird
         */
        playerSpielsteineTests.setSpielSteineSpieler(0);
        assertThat(playerSpielsteineTests.getSpielSteineSpieler(), is(0));
        assertTrue(playerSpielsteineTests.getSpielSteineSpieler() == 0);
        assertFalse(playerSpielsteineTests.getSpielSteineSpieler() != 0);

        playerSpielsteineTests.setSpielSteineSpieler(6);
        assertThat(playerSpielsteineTests.getSpielSteineSpieler(), is(6));
        assertTrue(playerSpielsteineTests.getSpielSteineSpieler() == 6);
        assertFalse(playerSpielsteineTests.getSpielSteineSpieler() != 6);

        playerSpielsteineTests.setSpielSteineSpieler(3);
        assertThat(playerSpielsteineTests.getSpielSteineSpieler(),is(3));
        assertTrue(playerSpielsteineTests.getSpielSteineSpieler() == 3);
        assertFalse(playerSpielsteineTests.getSpielSteineSpieler() != 3);

        playerSpielsteineTests.setSpielSteineSpieler(0);
        assertThat(playerSpielsteineTests.getSpielSteineSpieler(), is(0));
        assertTrue(playerSpielsteineTests.getSpielSteineSpieler() == 0);
        assertFalse(playerSpielsteineTests.getSpielSteineSpieler() != 0);
    }

    /**
     * Vollstaendige Runden Simulation
     */

    @Test
    public void GameSimulationTest() throws Exception
    {
        this.playerNameTests.setName("TestyTest");
        assertThat(playerNameTests.getName(), is("TestyTest"));
        assertTrue(playerNameTests.getName().equals("TestyTest"));
        assertFalse(!playerNameTests.getName().equals("TestyTest"));

        Player player = new Player("TestyBoy");
        assertThat(player.getName(), is("TestyBoy"));
        assertTrue(player.getName().equals("TestyBoy"));
        assertFalse(!player.getName().equals("TestyBoy"));

        player.setSpielSteineSpieler(8);
        assertThat(player.getSpielSteineSpieler(), is(8));
        assertTrue(player.getSpielSteineSpieler() == 8);
        assertFalse(player.getSpielSteineSpieler() != 8);

        for (int i = 7; i > 0 ; i--) {
            player.setSpielSteineSpieler(i);
            assertThat(player.getSpielSteineSpieler(), is(i));
            assertTrue(player.getSpielSteineSpieler() == i);
            assertFalse(player.getSpielSteineSpieler() != i);
        }

        player.setSpielSteineSpieler(0);
        assertTrue(player.getSpielSteineSpieler() == 0);
        assertFalse(player.getSpielSteineSpieler() != 0);

    }


    /**
     * getKI Test
     */

    @Test
    public void getKiTest() throws Exception
    {
        this.playerNameTests.setName("Bin ich eine KI?");
        assertThat(playerNameTests.getKI(), is(false));
        assertTrue(playerNameTests.getKI() == false);
        assertFalse(playerNameTests.getKI() != false);
    }

    /**
     * toString Test
     */

    @Test
    public void toStringTest() throws Exception
    {
        assertThat(playerToString.toString(), is("toStringTester: 0"));
        assertTrue(playerToString.toString().equals("toStringTester: 0"));
        assertFalse(!playerToString.toString().equals("toStringTester: 0"));
    }


    @Test
    public void toStringTestwithStones() throws Exception
    {
        playerToString.setSpielSteineSpieler(9);
        assertThat(playerToString.toString(), is("toStringTester: 9"));
        assertTrue(playerToString.toString().equals("toStringTester: 9"));
        assertFalse(!playerToString.toString().equals("toStringTester: 9"));

    }

    @Test
    public void toStringTestSigns() throws Exception
    {
        playerToString.setName("toStringT�ster!#");
        assertThat(playerToString.toString(), is("toStringT�ster!#: 0"));
        assertTrue(playerToString.toString().equals("toStringT�ster!#: 0"));
        assertFalse(!playerToString.toString().equals("toStringT�ster!#: 0"));
    }


    @Test
    public void toStringTestNumbers() throws Exception
    {
        playerToString.setName("9999");
        assertThat(playerToString.toString(), is("9999: 0"));
        assertTrue(playerToString.toString().equals("9999: 0"));
        assertFalse(!playerToString.toString().equals("9999: 0"));
    }


}