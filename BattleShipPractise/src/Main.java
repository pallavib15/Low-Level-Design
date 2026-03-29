import services.GameManager;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {

    GameManager gameManager=GameManager.getInstance();
    gameManager.initGame(6);
    gameManager.addShip(1,2,1,1,2,4);
    gameManager.addShip(2,2,3,0,4,5);

    gameManager.startGame();


}
