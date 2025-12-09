package Utility;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 * Generic barebones implementation of Swing's tree node interface
 * 
 * @author TomR
 * @param <T> generic node type
 */
public class TreeNode<T> implements javax.swing.tree.TreeNode
{
    T data;
    TreeNode<T> parent;
    List<TreeNode<T>> children;

    /**
     * Constructs a tree node 
     * @param data 
     */
    public TreeNode(T data)
            throws IllegalArgumentException
    {
        if (data == null)
        {
            throw new IllegalArgumentException("TreeNode<T>::Constructor:"
                    + " Received a NULL generic data object.");
        }
        this.data = data;
        this.children = new LinkedList<>();
    }

    public TreeNode<T> addChild(T child)
            throws IllegalArgumentException
    {
        if (child == null)
        {
            throw new IllegalArgumentException("TreeNode<T>::Constructor:"
                    + " Received a NULL generic data object.");
        }
        TreeNode<T> childNode = new TreeNode<>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    public T getData()
    {
        return this.data;
    }

    public List<TreeNode<T>> getChildren()
    {
        return this.children;
    }

    @Override
    public javax.swing.tree.TreeNode getChildAt(int childIndex)
    {
        if (childIndex < 0 || childIndex >= this.children.size()) return null;
        return this.children.get(childIndex);
    }

    @Override
    public int getChildCount()
    {
        return children.size();
    }

    @Override
    public javax.swing.tree.TreeNode getParent()
    {
        return parent;
    }

    @Override
    public int getIndex(javax.swing.tree.TreeNode node)
    {
        int index = -1;
        if (node != null)
        {
            for (int i=0;i<this.children.size();i++)
            {
                if (this.children.get(i).equals(node))
                {
                    index = i;
                }
            }
        }
        return index;
    }

    @Override
    public boolean getAllowsChildren()
    {
        return true;
    }

    @Override
    public boolean isLeaf()
    {
        return this.children.isEmpty();
    }

    @Override
    public Enumeration children()
    {
        return java.util.Collections.enumeration(this.children);
    }
}
