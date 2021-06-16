package PreProcess;

import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

public class DemoVisitor extends ASTVisitor {
    List<String> comments = new ArrayList<String>();

    @Override
    public boolean visit(FieldDeclaration node) {
        for (Object obj: node.fragments()) {
            VariableDeclarationFragment v = (VariableDeclarationFragment)obj;
            String comment = v.getName()+"";
            if(!comments.contains(comment)){
                comments.add(comment);
            }

            //System.out.println("Field:\t" + v.getName());
        }

        return true;
    }

    @Override
    public boolean visit(MethodDeclaration node) {
        //System.out.println("Method:\t" + node.getName());
        String comment = node.getName()+"";
        if(!comments.contains(comment)){
            comments.add(comment);
        }
        return true;
    }

    @Override
    public boolean visit(TypeDeclaration node) {
        //System.out.println("Class:\t" + node.getName());
        String comment = node.getName()+"";
        if(!comments.contains(comment)){
            comments.add(comment);
        }
        return true;
    }


}

