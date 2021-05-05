import java.io.*;
import java.util.*;

public class LinkedList<T>
{
	private Node<T> head;  // pointer to the front (first) element of the list

	public LinkedList()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// LOAD LINKED LIST FROM INCOMING FILE
	@SuppressWarnings("unchecked")
	public LinkedList( String fileName, boolean orderedFlag )
	{	head = null;
		try
		{
			BufferedReader infile = new BufferedReader( new FileReader( fileName ) );
			while ( infile.ready() )
			{
				if (orderedFlag)
					insertInOrder( (T)infile.readLine() );  // WILL INSERT EACH ELEM INTO IT'S SORTED POSITION
				else
					insertAtTail( (T)infile.readLine() );  // TACK EVERY NEWELEM ONTO END OF LIST. ORIGINAL ORDER PRESERVED
			}
			infile.close();
		}
		catch( Exception e )
		{
			System.out.println( "FATAL ERROR CAUGHT IN C'TOR: " + e );
			System.exit(0);
		}
	}

	//-------------------------------------------------------------

	// inserts new elem at front of list - pushing old elements back one place

	public void insertAtFront(T data)
	{
		head = new Node<T>(data,head);
	}

	// we use toString as our print

	public String toString()
	{
		String toString = "";

		for (Node curr = head; curr != null; curr = curr.next)
		{
			toString += curr.data;		// WE ASSUME OUR T TYPE HAS toString() DEFINED
			if (curr.next != null)
				toString += " ";
		}

		return toString;
	}

	// ########################## Y O U   W R I T E    T H E S E    M E T H O D S ########################


	@SuppressWarnings("unchecked")
	public int size() // OF COURSE MORE EFFICIENT to KEEP COUNTER BUT YOU  MUST WRITE LOOP
	{
		Node<T> curr = head;
		int count =0;
		while(curr!=null)
		{
			curr = curr.next;
			count++;
		}
		return count; // YOUR CODE HERE
	}
	public boolean empty()
	{
		if(size()==0)
		{
			return true;
		}
		return false;  // YOUR CODE HERE
	}

	public boolean contains( T key )
	{
		return search(key)!=null;  //true
	}
	@SuppressWarnings("unchecked")
	
	public Node<T> search( T key )
	{
		Node<T> curr = head;
        while(curr != null)
        {
            if(curr.data.equals(key))
            {
                return curr;
            }
            else
            {
                curr = curr.next;
            }
        }
        return null;
	}
@SuppressWarnings("unchecked")
	// TACK A NEW NODE (CABOOSE) ONTO THE END OF THE LIST
	public void insertAtTail(T data)
	{
		if(size()==0)
		{
			insertAtFront(data);
			return;
		}
		Node<T> curr = head;
		while(curr.next!=null)
		{
			curr=curr.next;
		}
		if(curr.next==null)
		{
			curr.next = new Node<T>(data, null);
			return;
		}
	}

	@SuppressWarnings("unchecked")  //CAST TO COMPARABLE THROWS WARNING
	public void insertInOrder(T data)
	{
		Comparable cdata = (Comparable) data;

		if(empty() || cdata.compareTo(head.data)<0)
		{
			insertAtFront(data);
			return;
		}
		Node<T> curr = head;
		while(curr.next!=null && cdata.compareTo(curr.next.data)>0)
		{
			curr=curr.next;
		}
		while(curr.next!=null && cdata.compareTo(curr.next.data)<=0)
		{	
			curr.next= new Node<T>(data, curr.next);
			return;
		}	
		insertAtTail(data);
		return;
	}
@SuppressWarnings("unchecked")
	public boolean remove(T key)
	{
			Node<T> curr = head;
			Node<T> prev = null;
			if(curr!=null && curr.data.equals(key))
			{
				head=curr.next;
				return true;
			}
			while(curr.next!=null && curr.data!=key)
			{
				prev=curr;
				curr= curr.next;
			}
			if(curr.data.equals(key))
			{
				prev.next=curr.next;
				return true;
			}
			return false;
	}
	@SuppressWarnings("unchecked")
	public boolean removeAtTail()	// RETURNS TRUE IF THERE WAS NODE TO REMOVE
	{
		if(size()==0)
		{
			return false;
		}
		if(size()==1)
		{
			head=null;
			return true;
		}
		Node<T> curr = head;
		while(curr.next.next!=null)
		{
			curr=curr.next;
		}
		curr.next=null;

		return true; // YOUR CODE HERE
	}
@SuppressWarnings("unchecked")
	public boolean removeAtFront() // RETURNS TRUE IF THERE WAS NODE TO REMOVE
	{
		if(empty())
		{
			return false;
		}
		else
		{
			head=head.next;
			return true;
		}
		 // YOUR CODE HERE
	}
	@SuppressWarnings("unchecked")
	public LinkedList<T> union( LinkedList<T> other )
	{
		LinkedList<T> union = new LinkedList<T>();
		Node<T> curr = this.head;
		Node<T> curr1= other.head;
		
		while(curr==null)
		{
			union.insertInOrder(curr.data);
		}
		while(curr!=null)
		{	
			union.insertInOrder(curr.data);
			curr=curr.next;
		}
		
		while(curr1.next!=null)
		{
			if(union.contains(curr1.data)==false)
				{
					union.insertInOrder(curr1.data);						
				}
			curr1=curr1.next;
		}
		if(union.contains(curr1.data)==false)
		{
			union.insertAtTail(curr1.data);
		}
		return union;
	}
	@SuppressWarnings("unchecked")
	public LinkedList<T> inter( LinkedList<T> other )
	{
		LinkedList<T> inter = new LinkedList<T>();
		Node<T> curr = this.head;
		Node<T> curr1= other.head;
		int size = 0;
		if(this.size()>other.size())
		{
			while(curr.next!=null)
			{
				if(other.contains(curr.data)==true)
				{
					inter.insertInOrder(curr.data);
				}
				curr=curr.next;
			}
		}
		else
		{
			while(curr1.next!=null)
			{
				if(this.contains(curr1.data)==true)
				{
					inter.insertInOrder(curr1.data);
				}
				curr1=curr1.next;
			}
		}
		return inter;
	}
	@SuppressWarnings("unchecked")
	public LinkedList<T> diff( LinkedList<T> other )
	{
		LinkedList<T> diff = new LinkedList<T>();
		Node<T> curr = this.head;
		Node<T> curr1= other.head;
		while(curr!=null)
		{
			if(other.contains(curr.data)==false)
			{
				diff.insertInOrder(curr.data);
			}
			curr=curr.next;
		}
		return diff;
	}
	public LinkedList<T> xor( LinkedList<T> other )
	{
		
		return (this.union(other).diff(this.inter(other)));  
	
	} //END LINKEDLIST CLASS

}// A D D   N O D E   C L A S S  D O W N   H E R E 
// R E M O V E  A L L  P U B L I C  &  P R I V A T E (except toString)
// R E M O V E  S E T T E R S  &  G E T T E R S 
// M A K E  T O  S T R I N G  P U B L I C
// copy this class code into the linked list file and get rid of this file 

 class Node<T>
{
   T  data;
   Node next;

   Node()
  {
    this( null, null );
  }

   Node(T data)
  {
    this( data, null );
  }

  Node(T data, Node next)
  {
    this.data=data;
    this.next=next;
  }

  public String toString()
  {
	  return ""+this.data;
  } 
} //EOF