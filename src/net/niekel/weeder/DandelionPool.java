package net.niekel.weeder;

import org.andengine.util.adt.pool.GenericPool;

public class DandelionPool extends GenericPool<DandelionSprite> {
		
	public static DandelionPool instance;
	 
    public static DandelionPool getSharedInstance() {
        if (instance == null)
            instance = new DandelionPool();
        return instance;
    }
 
    /*
    private DandelionPool() {
        super();
    }
    */

	@Override
	protected DandelionSprite onAllocatePoolItem() {
		return new DandelionSprite();
	}

	protected void onHandleRecycleItem(final DandelionSprite d) {
		d.clearUpdateHandlers();
		d.detachSelf();
    }
}
