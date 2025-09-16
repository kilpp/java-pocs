
package com.example.crdt;

import com.netopyr.wurmloch.crdt.GCounter;
import com.netopyr.wurmloch.crdt.PNCounter;
import com.netopyr.wurmloch.store.CrdtStore;
import com.netopyr.wurmloch.store.LocalCrdtStore;

public class Main {

    public static void main(String[] args) {
        // Create two CrdtStores, representing two different nodes
        LocalCrdtStore crdtStore1 = new LocalCrdtStore();
        LocalCrdtStore crdtStore2 = new LocalCrdtStore();

        // Connect the stores to enable synchronization
        crdtStore1.connect(crdtStore2);

        // G-Counter Example
        System.out.println("G-Counter Example:");
        // 1. Create a G-Counter on the first store with a unique ID
        GCounter gCounterReplica1 = crdtStore1.createGCounter("my-g-counter");

        // 2. Find the corresponding replica of the G-Counter on the second store
        GCounter gCounterReplica2 = crdtStore2.<GCounter>findGCounter("my-g-counter").get();

        // 3. Increment the counter on both replicas
        gCounterReplica1.increment();      // Increment by 1
        gCounterReplica2.increment(2L);    // Increment by 2

        // 4. The stores are connected, so the replicas synchronize automatically.
        // The value on both replicas will converge to the total sum of increments (1 + 2 = 3).
        System.out.println("Replica 1 value: " + gCounterReplica1.get()); // Outputs: 3
        System.out.println("Replica 2 value: " + gCounterReplica2.get()); // Outputs: 3

        // Disconnect the stores to simulate a network partition
        crdtStore1.disconnect(crdtStore2);

        // Increment the replicas while they are disconnected
        gCounterReplica1.increment(5L);
        gCounterReplica2.increment(10L);

        // The replicas now have diverged values
        System.out.println("Replica 1 value (disconnected): " + gCounterReplica1.get()); // Outputs: 8 (3 + 5)
        System.out.println("Replica 2 value (disconnected): " + gCounterReplica2.get()); // Outputs: 13 (3 + 10)

        // Reconnect the stores
        crdtStore1.connect(crdtStore2);

        // After reconnection, the values automatically merge and converge again
        System.out.println("Replica 1 value (reconnected): " + gCounterReplica1.get()); // Outputs: 18 (8 + 10)
        System.out.println("Replica 2 value (reconnected): " + gCounterReplica2.get()); // Outputs: 18 (13 + 5)

        System.out.println("\nPN-Counter Example:");
        // PN-Counter Example
        // 1. Create a PN-Counter on the first store with a unique ID
        PNCounter pnCounterReplica1 = crdtStore1.createPNCounter("my-pn-counter");

        // 2. Find the corresponding replica of the PN-Counter on the second store
        PNCounter pnCounterReplica2 = crdtStore2.<PNCounter>findPNCounter("my-pn-counter").get();

        // 3. Increment on replica 1, decrement on replica 2
        pnCounterReplica1.increment(10L);
        pnCounterReplica2.decrement(3L);

        // 4. The stores are connected, so the replicas synchronize automatically.
        System.out.println("Replica 1 value: " + pnCounterReplica1.get()); // Outputs: 7
        System.out.println("Replica 2 value: " + pnCounterReplica2.get()); // Outputs: 7

        // Disconnect the stores
        crdtStore1.disconnect(crdtStore2);

        // Increment replica 1, decrement replica 2
        pnCounterReplica1.increment(5L);
        pnCounterReplica2.decrement(2L);

        // The replicas now have diverged values
        System.out.println("Replica 1 value (disconnected): " + pnCounterReplica1.get()); // Outputs: 12
        System.out.println("Replica 2 value (disconnected): " + pnCounterReplica2.get()); // Outputs: 5

        // Reconnect the stores
        crdtStore1.connect(crdtStore2);

        // After reconnection, the values automatically merge and converge again
        System.out.println("Replica 1 value (reconnected): " + pnCounterReplica1.get()); // Outputs: 10
        System.out.println("Replica 2 value (reconnected): " + pnCounterReplica2.get()); // Outputs: 10
    }
}

