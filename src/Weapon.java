/**
 * Created by Victor Dichko on 22.11.14.
 */
public class Weapon {
    private String name;
    private int reloadTime;
    private BulletType bulletType;
    private int bulletsNumber;
    private int leftDelayForShot;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLeftDelayForShot() {
        return leftDelayForShot;
    }

    public void setLeftDelayForShot(int leftDelayForShot) {
        this.leftDelayForShot = leftDelayForShot;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public BulletType getBulletType() {
        return bulletType;
    }

    public int getBulletsNumber() {
        return bulletsNumber;
    }

    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public void setBulletType(BulletType bulletType) {
        this.bulletType = bulletType;
    }

    public void setBulletsNumber(int bulletsNumber) {
        this.bulletsNumber = bulletsNumber;
    }

    public Bullet shot() {
        if (this.bulletsNumber > 0 ) {
            bulletsNumber--;
            Bullet bullet = new Bullet();
            bullet.setType(this.bulletType);
            bullet.setHealth(-1);
            return bullet;
        } else {
            return null;
        }
    }
}
