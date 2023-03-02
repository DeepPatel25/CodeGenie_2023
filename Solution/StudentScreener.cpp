#include <bits/stdc++.h>
using namespace std;

string join(vector<string> &vi, char c)
{
    string ans = "";
    for (int i = 0; i < vi.size(); i++)
    {
        ans += vi[i];
        if (i < vi.size() - 1)
        {
            ans += c;
        }
    }
    return ans;
}

string getEligibleStudents(int totalStudents, vector<string> students, vector<int> &marks, vector<string> &examResults, int totalMarks, float requiredPercentile)
{
    int n = students.size();
    map<int, string> m1;
    map<int, string> m2;

    for (int i = 0; i < n; i++)
    {
        m1[marks[i]] = students[i];
        m2[marks[i]] = examResults[i];
    }

    int requiredRank = n - (int)(requiredPercentile * n / 100);
    sort(marks.begin(), marks.end());

    vector<string> vi;
    for (int i = n - 1; i > n - requiredRank; i--)
    {
        if (m2[marks[i]] == "Passed")
        {
            vi.push_back(m1[marks[i]]);
        }
    }

    string ans = join(vi, ',');
    return ans;
}

int main()
{
    int totalStudents = 7;
    int totalMarks = 1000;
    float requiredPercentile = 50;
    vector<int> marks = {800, 300, 750, 760, 680, 790, 640};
    vector<string> students = {"Kartik", "Devang", "Pari", "Ketan", "Sheetal", "Darshana", "Mohan"};
    vector<string> examResults = {"Passed", "Failed", "Passed", "Failed", "Passed", "Passed", "Passed"};
    string eligibleStudents = getEligibleStudents(totalStudents, students, marks, examResults, totalMarks, requiredPercentile);
    cout << eligibleStudents << endl;
    return 0;
}
