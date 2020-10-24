public class Biomes extends Thread {
    GamePanel panel;
    int s = 50;
    int windowHeight = 1100;//1100, da bei 1080 immer 20 px verschiebung war
    int bottomRow = windowHeight - 150;
    //Import Images for the different solids
    String imageWall = "wall.png"; //Placeholder
    String imageStone = "stone.png"; //Placeholder


    public void Mountain1(){
        panel = new GamePanel();
        //BottomLayer Texture: Stone
        for(int i=0; i<40; i++) {
            panel.walls.add(new Wall((panel.offset + i*50), bottomRow, s, s, imageStone));
        }
        //Grass or dirt
        for(int i=2; i<= 4; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-50, s, s, imageWall));
        panel.walls.add(new Wall((panel.offset+ 4*50), (bottomRow-100), s, s, imageWall));
        for(int i=4; i<= 6; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-150, s, s, imageWall));
        for(int i=6; i<= 9; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-100, s, s, imageWall));
        panel.walls.add(new Wall((panel.offset+ 9*50), bottomRow-150, s, s, imageWall));
        for(int i=9; i<= 11; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-200, s, s, imageWall));
        for(int i=11; i<= 13; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-250, s, s, imageWall));
        for(int i=14; i<= 15; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-300, s, s, imageWall));
        for(int i=15; i<= 19; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-350, s, s, imageWall));
        for(int i=15; i<= 16; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-400, s, s, imageWall));
        for(int i=19; i<= 23; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-500, s, s, imageWall));
        for(int i=21; i<= 22; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-550, s, s, imageWall));
        for(int i=22; i<= 23; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-500, s, s, imageWall));
        for(int i=23; i<= 26; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-450, s, s, imageWall));
        panel.walls.add(new Wall((panel.offset+ 27*50), bottomRow-400, s, s, imageWall));
        for(int i=27; i<= 28; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-350, s, s, imageWall));
        for(int i=28; i<= 29; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-300, s, s, imageWall));
        for(int i=29; i<= 30; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-250, s, s, imageWall));
        for(int i=30; i<= 36; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-200, s, s, imageWall));
        for(int i=33; i<= 34; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-250, s, s, imageWall));
        panel.walls.add(new Wall((panel.offset+ 36*50), bottomRow-150, s, s, imageWall));
        for(int i=36; i<= 39; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-100, s, s, imageWall));
        panel.walls.add(new Wall((panel.offset+ 39*50), bottomRow-50, s, s, imageWall));
        panel.walls.add(new Wall((panel.offset+ 14*50), bottomRow-250, s, s, imageWall));
        panel.walls.add(new Wall((panel.offset+ 26*50), bottomRow-400, s, s, imageWall));

        //Stone
        for(int i=4; i<= 8; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-50, s, s, imageStone));
        for(int i=15; i<= 38; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-50, s, s, imageStone));
        panel.walls.add(new Wall((panel.offset+ 5*50), bottomRow-100, s, s, imageStone));
        for(int i=16; i<= 19; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-100, s, s, imageStone));
        for(int i=24; i<= 35; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-100, s, s, imageStone));
        for(int i=17; i<= 18; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-150, s, s, imageStone));
        for(int i=26; i<= 35; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-150, s, s, imageStone));
        panel.walls.add(new Wall((panel.offset+ 21*50), bottomRow-200, s, s, imageStone));
        for(int i=27; i<= 29; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-200, s, s, imageStone));
        for(int i=21; i<= 24; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-250, s, s, imageStone));
        for(int i=27; i<= 28; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-250, s, s, imageStone));
        for(int i=16; i<= 21; i++) panel.walls.add(new Wall((panel.offset+ i*50), bottomRow-300, s, s, imageStone));
        panel.walls.add(new Wall((panel.offset+ 20*50), bottomRow-350, s, s, imageStone));
    }
}
