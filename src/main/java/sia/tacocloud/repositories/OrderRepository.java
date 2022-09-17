package sia.tacocloud.repositories;

import sia.tacocloud.data.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
