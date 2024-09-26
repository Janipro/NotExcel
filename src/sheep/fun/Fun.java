package sheep.fun;

import sheep.core.SheetUpdate;

/**
 * @provided
 */
public interface Fun {
    void draw(SheetUpdate sheet) throws FunException;
}
