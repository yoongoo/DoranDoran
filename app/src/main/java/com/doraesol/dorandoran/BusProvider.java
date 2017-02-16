package com.doraesol.dorandoran;

import com.squareup.otto.Bus;

/**
 * Created by YOONGOO on 2017-02-08.
 */

public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}
