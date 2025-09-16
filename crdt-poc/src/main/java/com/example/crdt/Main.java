
package com.example.crdt;

import com.netopyr.wurmloch.crdt.GCounter;
import com.netopyr.wurmloch.store.CrdtStore;
import com.netopyr.wurmloch.store.LocalCrdtStore;

public class Main {

    public static void main(String[] args) {
        // Create two CrdtStores, representing two different nodes
        LocalCrdtStore crdtStore1 = new LocalCrdtStore();
        LocalCrdtStore crdtStore2 = new LocalCrdtStore();

        // Connect the stores to enable synchronization
        crdtStore1.connect(crdtStore2);

        // 1. Create a G-Counter on the first store with a unique ID
        GCounter replica1 = crdtStore1.createGCounter("my-counter");

        // 2. Find the corresponding replica of the G-Counter on the second store
        GCounter replica2 = crdtStore2.<GCounter>findGCounter("my-counter").get();

        // 3. Increment the counter on both replicas
        replica1.increment();      // Increment by 1
        replica2.increment(2L);    // Increment by 2

        // 4. The stores are connected, so the replicas synchronize automatically.
        // The value on both replicas will converge to the total sum of increments (1 + 2 = 3).
        System.out.println("Replica 1 value: " + replica1.get()); // Outputs: 3
        System.out.println("Replica 2 value: " + replica2.get()); // Outputs: 3

        // Disconnect the stores to simulate a network partition
        crdtStore1.disconnect(crdtStore2);

        // Increment the replicas while they are disconnected
        replica1.increment(5L);
        replica2.increment(10L);

        // The replicas now have diverged values
        System.out.println("Replica 1 value (disconnected): " + replica1.get()); // Outputs: 8 (3 + 5)
        System.out.println("Replica 2 value (disconnected): " + replica2.get()); // Outputs: 13 (3 + 10)

        // Reconnect the stores
        crdtStore1.connect(crdtStore2);

        // After reconnection, the values automatically merge and converge again
        System.out.println("Replica 1 value (reconnected): " + replica1.get()); // Outputs: 18 (8 + 10)
        System.out.println("Replica 2 value (reconnected): " + replica2.get()); // Outputs: 18 (13 + 5)
    }
}
