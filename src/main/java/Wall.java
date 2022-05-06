import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

interface Structure {
    // zwraca dowolny element o podanym kolorze
    Optional findBlockByColor(String color);

    // zwraca wszystkie elementy z danego materiału
    List findBlocksByMaterial(String material);

    //zwraca liczbę wszystkich elementów tworzących strukturę
    int count();
}

public class Wall implements Structure {
    private List<CompositeBlock> blocks;

    @Override
    public Optional<CompositeBlock> findBlockByColor(String color) {
        return blocks.stream()
                .filter(b -> b.getColor().equals(color))
                .findAny();
    }

    @Override
    public List<CompositeBlock> findBlocksByMaterial(String material) {
        return blocks.stream()
                .filter(b -> b.getMaterial().equals(material))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        int result = 0;
        for (CompositeBlock block : blocks) {
            result += block.getBlocks().size();
        }
        return result;
    }
}

interface Block {
    String getColor();

    String getMaterial();
}


interface CompositeBlock extends Block {
    List getBlocks();
}