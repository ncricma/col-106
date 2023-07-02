import java.io.*; 
import java.util.*; 

// Tree node
class Node {
	int id,level;
	Node parent;
	Vector<Node> child;
	Node(int id,Node parent)
	{
		this.id=id;
		this.parent=parent;
		if(parent==null)
			this.level=1;
		else
			this.level=parent.level+1;
	}
	Node(int id)
	{
		this(id,null);
	}
	void setChild(int id)
	{
		if(this.child==null)
			this.child=new Vector<Node>();
		this.child.add(new Node(id,this));
		
	}
	Node get(int id)
	{
		if(this.id==id)
		 return this;
		else
		{
			if(this.child==null)
				return null;
			for(Node curr:this.child)
			{
				Node temp=curr.get(id);
				if(temp!=null)
					return temp;

			}
		}
		return null;
	}
  
}


public class OrgHierarchy implements OrgHierarchyInterface{

//root node
Node root;
private int size;

public boolean isEmpty(){
	//your implementation
	return root==null;
	 //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
} 

public int size(){
	//your implementation
	if(root ==null)
		return 0;
	else
		return size;
	//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}

public int level(int id) throws IllegalIDException, EmptyTreeException{
	//your implementation
	if (root==null)
		throw new EmptyTreeException("");
	Node curr=root.get(id);
	if(curr==null)
		throw new IllegalIDException("");
	else
		return curr.level;

	 //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
} 

public void hireOwner(int id) throws NotEmptyException{
	//your implementation
	if(root== null){
		root=new Node(id);size=1;}
	else
		throw new NotEmptyException("");
	 //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}

public void hireEmployee(int id, int bossid) throws IllegalIDException, EmptyTreeException{
	//your implementation
	if (root==null)
		throw new EmptyTreeException("");
	if(id==root.id)
		throw new IllegalIDException("");
	else
	{
		Node bossy=root.get(bossid);
		bossy.setChild(id);size++;
	}	
	 //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
} 

public void fireEmployee(int id) throws IllegalIDException,EmptyTreeException{
	//your implementation
	if(root ==null)
		throw new EmptyTreeException("");
	if(root.id==id)
		throw new IllegalIDException("");
	Node useless=root.get(id);
	if(useless.child!=null)
		throw new IllegalIDException("");
	useless.parent.child.remove(useless);
	size--;

 	//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}
public void fireEmployee(int id, int manageid) throws IllegalIDException,EmptyTreeException{
	//your implementation
	if(root ==null)
		throw new EmptyTreeException("");
	if(root.id==id)
		throw new IllegalIDException("");
	Node one=root.get(id),two=root.get(manageid);
	if(one==null || two==null)
		throw new IllegalIDException("");
	if(one.level!=two.level)
		throw new IllegalIDException("");
	one.parent.child.remove(one);size--;
	if(one.child==null)
		return;
	if(two.child==null)
		two.child=new Vector<Node>();
	for(Node b:one.child)
	{
		two.child.add(b);
		b.parent=two;
	}
	
	 //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
} 

public int boss(int id) throws IllegalIDException,EmptyTreeException{
	//your implementation
	if(root==null)
		throw new EmptyTreeException("");
	else
	{
		Node curr=root.get(id);
		if(curr==null)
			throw new IllegalIDException("");
		return (curr.level==1)?-1:(curr.parent.id);
	}
	 //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}

public int lowestCommonBoss(int id1, int id2) throws IllegalIDException,EmptyTreeException{
	//your implementation
	if(root==null)
		throw new EmptyTreeException("");
	Node one=root.get(id1),two=root.get(id2);
	if(one==null || two==null || id1==id2)
		throw new IllegalIDException("");
	if(id1==root.id || root.id==id2)
		return -1;
	while(one.level<two.level)
	{
		two=two.parent;
	}
	while(one.level>two.level)
	{
		one=one.parent;
	}
	if(one==two)
		return one.parent.id;
	while(one!=two)
	{
		one=one.parent;two=two.parent;
	}
	return one.id;
	// throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}
private void sort(int a[],int i,int j)
{
	if(i>=j)
		return;
	int pivot=a[i];
	int x=i,y=j;
	while(x<=y)
	{
		while(x<=j && a[x]<pivot)
			x++;
		while(y>=i && a[y]>=pivot)
			y--;
		if(x<=y)
		{
			int t=a[x];a[x]=a[y];a[y]=t;x++;y--;
		}
	}
	sort(a,i,y);
	if(x==i)
		sort(a,x+1,j);
	else
		sort(a,x,j);

}
public String toString(int id) throws IllegalIDException, EmptyTreeException{
	//your implementation
	if(root==null)
		throw new EmptyTreeException("");
	Node t=root.get(id);
	if(t==null)
		throw new IllegalIDException("");
	String s=""+t.id;
	Vector<Node> temp=t.child;
	if(temp==null) return s;
	Vector<Node> temp2=new Vector<Node>();
	while(temp.size()!=0)
	{
		s+=",";
		int a[]=new int[temp.size()],i=0;
		for(Node b:temp)
		{
			a[i++]=b.id;
			if(b.child!=null)
			for(Node e:b.child)
				temp2.add(e);
		}
		sort(a,0,a.length-1);
		for(i=0;i<a.length-1;i++)
		{
			s+=a[i]+" ";
		}
		s+=a[a.length-1];
		temp=temp2;
		temp2=new Vector<Node>();
	}
	return s;
	//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}

}

