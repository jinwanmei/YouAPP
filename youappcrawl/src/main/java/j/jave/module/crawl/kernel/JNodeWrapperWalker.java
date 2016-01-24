/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package j.jave.module.crawl.kernel;

import java.util.List;
import java.util.Stack;

public class JNodeWrapperWalker {

  // the root node the the stack holding the nodes
  private JNodeWrapper currentNode;
  private List<JNodeWrapper> currentChildren;
  private Stack<JNodeWrapper> nodes;

  /**
   * Starts the <code>JNodeWrapper</code> tree from the root node.
   * 
   * @param rootNode
   */
  public JNodeWrapperWalker(JNodeWrapper rootNode) {

    nodes = new Stack<JNodeWrapper>();
    nodes.add(rootNode);
  }

  /**
   * <p>
   * Returns the next <code>JNodeWrapper</code> on the stack and pushes all of its
   * children onto the stack, allowing us to walk the node tree without the use
   * of recursion. If there are no more nodes on the stack then null is
   * returned.
   * </p>
   * 
   * @return JNodeWrapper The next <code>JNodeWrapper</code> on the stack or null if there isn't
   *         a next node.
   */
  public JNodeWrapper nextNode() {

    // if no next node return null
    if (!hasNext()) {
      return null;
    }

    // pop the next node off of the stack and push all of its children onto
    // the stack
    currentNode = nodes.pop();
    currentChildren = currentNode.getChildren();
    int childLen = (currentChildren != null) ? currentChildren.size() : 0;

    // put the children node on the stack in first to last order
    for (int i = childLen - 1; i >= 0; i--) {
      nodes.add(currentChildren.get(i));
    }

    return currentNode;
  }
  
  public JNodeWrapper peekNextNode(){
	  return nodes.peek();
  }

  /**
   * <p>
   * Skips over and removes from the node stack the children of the last node.
   * When getting a next node from the walker, that node's children are
   * automatically added to the stack. You can call this method to remove those
   * children from the stack.
   * </p>
   * 
   * <p>
   * This is useful when you don't want to process deeper into the current path
   * of the node tree but you want to continue processing sibling nodes.
   * </p>
   * 
   */
  public void skipChildren() {

    int childLen = (currentChildren != null) ? currentChildren.size() : 0;

    for (int i = 0; i < childLen; i++) {
      JNodeWrapper child = nodes.peek();
      if (child.equals(currentChildren.get(i))) {
        nodes.pop();
      }
    }
  }
  
  
  private boolean isMatchParent(JNodeWrapper thisNode,JNodeWrapper parent){
	  if(parent.equals(thisNode)) return true;
	  JNodeWrapper currentNodeParent=thisNode.getParent();
		boolean matchesParent=false;
		do{
			if(parent.equals(currentNodeParent)){
				matchesParent=true;
				break;
			}
		}while((currentNodeParent=currentNodeParent.getParent())!=null);
		return matchesParent;
  }
  
  	/**
  	 * the parent must be parent of the current node.
  	 * @param parent
  	 */
  	public void skipChildrenWithParent(JNodeWrapper parent) {

  		if(!isMatchParent(currentNode, parent)){
  			throw new RuntimeException("the arguemnt must be parent of the current node.");
  		}
  		JNodeWrapper thisNodeWrapper=null;
  		while(nodes.size()>0&&(thisNodeWrapper=nodes.peek())!=null){
  			
  			if(thisNodeWrapper.equals(parent)){
  				nodes.pop();
  				break;
  			}
  			
  			if(isMatchParent(thisNodeWrapper, parent)){
  				nodes.pop();
  				continue;
  			}
  			// break the loop, no node under the parent.
  			break;
  		}
	  }
  

  /**
   * Return the current node.
   * 
   * @return JNodeWrapper
   */
  public JNodeWrapper getCurrentNode() {
    return currentNode;
  }

  /**
   * * Returns true if there are more nodes on the current stack.
   * 
   * @return
   */
  public boolean hasNext() {
    return (nodes.size() > 0);
  }
}
