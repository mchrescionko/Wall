import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
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
    private List<Block> blocks;

    public List<Block> unwindBlocks() {
        List<Block> singleBlock = new ArrayList<>();
        Stack<Block> toCheck = new Stack<>();
        toCheck.addAll(blocks);
        while (!toCheck.empty()) {
            Block check = toCheck.pop();
            singleBlock.add(check);
            try {
                CompositeBlock composite = (CompositeBlock) check;
                toCheck.addAll(composite.getBlocks());
            } catch (Exception ex) {
            }
        }
        return singleBlock;
    }

    @Override
    public Optional findBlockByColor(String color) {
        return unwindBlocks().stream().filter(e -> e.getColor().equals(color)).findAny();
    }

    @Override
    public List findBlocksByMaterial(String material) {
        return unwindBlocks().stream().filter(e -> e.getMaterial().equals(material)).collect(Collectors.toList());
    }

    @Override
    public int count() {
        return unwindBlocks().size();
    }

    interface Block {
        String getColor();

        String getMaterial();
    }

    interface CompositeBlock extends Block {
        List getBlocks();
    }
}
