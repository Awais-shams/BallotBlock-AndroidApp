// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract Election {

    struct Candidate {
        string name;
        bool registered;
        bool invited;
        uint voteCount;
    }

    struct Voter {
        bool voted;
        bool registered;
        address vote;
    }

    address[] public candidateAddresses;
    address public owner;

    string public electionName;

    mapping(address => Voter) public voters;
    mapping(address => Candidate) public candidates;

    uint public totalVotes;

    enum TypeOfState { Created, Voting, Ended }
    TypeOfState public globalState;

    // modifier is called preReq to function
    modifier onlyOwner() {
        // if this statement is true then only function will run else system will throw error.
        // _; is put to notify that real function will start from here.
        require(msg.sender == owner, "You are not the owner");
        _;
    }

    modifier systemState(TypeOfState _state) {
        require(globalState == _state, "Wrong State");
        _;
    }

    // memory is used so variable will only be saved till program execution time.
    // msg.sender contains the address of the person who called the function.
    // when SC is deployed system enters into Created state
    constructor(string memory _name) {
        owner = msg.sender;
        electionName = _name;
        globalState = TypeOfState.Created;
    }

    function getStatus() public view returns(TypeOfState) {
        return globalState;
    }

    // this function registers invited candidate where msg.value must be equal to 50wei
    function registerCandidate() public payable {
        require(msg.value == 50 wei, "Pay 50 wei to register");
        require(candidates[msg.sender].invited, "Candidate is not invited in this election");
        candidates[msg.sender].registered = true;
    }

    // this function invites new candidates to participate in elections
    function inviteCandidate(address candidate) public onlyOwner {
        candidates[candidate].invited = true;
    }

    // this function registers a voter
    // onlyOwner = modifier (preReq of this function)
    function registerVoter(address _voterAddress) onlyOwner systemState(TypeOfState.Created) public {
        require(!voters[_voterAddress].registered, "Voter is already registered");
        require(_voterAddress != owner, "Owner cannot be registered");
        voters[_voterAddress].registered = true;
    }

    // this function adds a new candidate, intialises it and pushes it to candidateAddresses Array
    function addCandidate(address _candidateAddress, string memory _name) systemState(TypeOfState.Created) onlyOwner public {
        candidates[_candidateAddress].name = _name;
        candidates[_candidateAddress].voteCount = 0;
        candidates[_candidateAddress].invited = true;
        candidateAddresses.push(_candidateAddress);
    }

    // this function sets the state to Voting, means now voting has started
    function startVoting() public systemState(TypeOfState.Created) onlyOwner {
        globalState = TypeOfState.Voting;
    }

    // this function casts a vote
    function vote(address _candidateAddress) systemState(TypeOfState.Voting) public {
        require(voters[msg.sender].registered, "Voter is not registered");
        require(!voters[msg.sender].voted, "Voter has already voted");
        require(candidates[_candidateAddress].registered, "Not a registered candidate");
        require(msg.sender!=owner, "Owner cannot vote");

        voters[msg.sender].vote = _candidateAddress;
        voters[msg.sender].voted = true;
        candidates[_candidateAddress].voteCount++;
        totalVotes++;
    }

    // this function changes the state to ended i.e voting has ended.
    function endVoting() public systemState(TypeOfState.Voting) onlyOwner {
        globalState = TypeOfState.Ended;
    }

    // this function announces winner
    function getWinner() systemState(TypeOfState.Ended) onlyOwner public view returns (address) {
        uint max = 0;
        address winnerAddress;
        for(uint i=0; i<candidateAddresses.length; i++) {
            if(candidates[candidateAddresses[i]].voteCount > max) {
                max = candidates[candidateAddresses[i]].voteCount;
                winnerAddress = candidateAddresses[i];
            }
        }
        return winnerAddress;
    }

    // this function returns number of candidates i.e returns candiateAddresses array length
    function getTotalNumberOfCandidates() public view returns(uint) {
        return candidateAddresses.length;
    }

    // this function returns the Total Balance of the contract which it got through candidates registration fees.
    function balanceOf() public view returns(uint) {
        return address(this).balance;
    }

    function getCandidateVotes(address _candidateAddress) public view onlyOwner returns(uint) {
        return candidates[_candidateAddress].voteCount;
    }

}