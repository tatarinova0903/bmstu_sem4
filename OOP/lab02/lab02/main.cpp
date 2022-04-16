#include <iostream>
#include "list.h"

using namespace std;

int main()
{
    try
    {
        cout << "Creating lists..." << endl;
        
        List<int> list1;
        int arr[10] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        List<int> list2(arr, 10);
        List<int> list3(list2);
        
        if (list1.is_empty())
            cout << "list1 is empty" << endl;
        
        if (list2 == list3)
            cout << "list2 == list3" << endl;
        
        if (list1 != list3)
            cout << "list1 != list3" << endl;
        
        list1 = list3;
        if (list1 == list3)
            cout << "list1 == list3" << endl;
        
        list1 += 1;
        list3 = list2 + 2;

        if (list3.is_empty())
            cout << "list3 is empty" << endl;
        else
            cout << "list3 is not empty" << endl;
        
        cout << list3.first() << endl;
        cout << list3.last() << endl;
        cout << list3.pop_front() << endl;
        cout << list3.pop_front() << endl;

        list3.push_back(3);
        list3.push_front(2);

        auto end = list3.end();
        for(auto it = list3.begin(); it != end; ++it)
        {
            int i = *it;
            cout << i << endl;
        }
    }
    catch(base_exception& error)
    {
        error.what();
    }

    return 0;
}
